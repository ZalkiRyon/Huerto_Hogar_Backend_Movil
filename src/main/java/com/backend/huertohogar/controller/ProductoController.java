package com.backend.huertohogar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.huertohogar.dto.ProductoRequestDTO;
import com.backend.huertohogar.dto.ProductoResponseDTO;
import com.backend.huertohogar.service.CloudinaryService;
import com.backend.huertohogar.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductoController(ProductoService productoService, CloudinaryService cloudinaryService) {
        this.productoService = productoService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> getAllProductos() {
        List<ProductoResponseDTO> productos = productoService.getAllProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
        // code 200
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductoResponseDTO>> getAllProductosIncludingInactive() {
        List<ProductoResponseDTO> productos = productoService.getAllProductosIncludingInactive();
        return new ResponseEntity<>(productos, HttpStatus.OK);
        // code 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> getProductoById(@PathVariable Integer id) {
        return productoService.getProductoById(id)
                .map(productoDTO -> new ResponseEntity<>(productoDTO, HttpStatus.OK))
                // code 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // code 404
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductoResponseDTO>> getProductosByCategory(@PathVariable String category) {
        List<ProductoResponseDTO> productos = productoService.getProductosByCategory(category);
        return new ResponseEntity<>(productos, HttpStatus.OK);
        // code 200
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> createProducto(@Valid @RequestBody ProductoRequestDTO productoDTO) {
        ProductoResponseDTO newProductoResponse = productoService.saveProducto(productoDTO);
        return new ResponseEntity<>(newProductoResponse, HttpStatus.CREATED);
        // code 201
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductoResponseDTO> updateProducto(@PathVariable Integer id,
            @Valid @RequestBody ProductoRequestDTO productoDTO) {
        ProductoResponseDTO updatedProductoResponse = productoService.updateProducto(id, productoDTO);
        return new ResponseEntity<>(updatedProductoResponse, HttpStatus.OK);
        // code 200
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // code 204
    }

    @PatchMapping("/{id}/reactivar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reactivateProducto(@PathVariable Integer id) {
        productoService.reactivateProducto(id);
        return new ResponseEntity<>(HttpStatus.OK);
        // code 200
    }

    @GetMapping("/{id}/nombre")
    public ResponseEntity<?> getProductName(@PathVariable Integer id) {
        Optional<String> productName = productoService.getProductNameById(id);
        return productName.map(name -> new ResponseEntity<>(name, HttpStatus.OK))
                // code 200
                .orElseGet(() -> new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND));
                // code 404
    }

    /**
     * Sube una imagen a Cloudinary y la asocia al producto.
     * Endpoint: POST /api/productos/{id}/imagen
     * 
     * @param id ID del producto
     * @param file Imagen a subir (multipart/form-data)
     * @return URL de la imagen subida
     */
    @PostMapping("/{id}/imagen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadProductImage(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Subir imagen a Cloudinary
            String imageUrl = cloudinaryService.uploadImage(file, "productos");
            
            // Actualizar producto con la URL
            productoService.updateProductImage(id, imageUrl);
            
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
            // code 200
        } catch (Exception e) {
            return new ResponseEntity<>("Error al subir imagen: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            // code 500
        }
    }
}
