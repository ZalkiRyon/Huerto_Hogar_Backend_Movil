package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.ProductoRequestDTO;
import com.backend.huertohogar.dto.ProductoResponseDTO;
import com.backend.huertohogar.exception.ResourceNotFoundException;
import com.backend.huertohogar.exception.ValidationException;
import com.backend.huertohogar.model.Categoria;
import com.backend.huertohogar.model.Producto;
import com.backend.huertohogar.repository.CategoriaRepository;
import com.backend.huertohogar.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ProductoResponseDTO> getAllProductos() {
        // Filtrar solo los activos si se desea, o todos.
        // Para este caso, mostraremos todos pero el borrado es lógico.
        // Si se quisiera solo activos: return productoRepository.findByActivoTrue()...
        return productoRepository.findAll().stream()
                .filter(p -> p.getActivo() == null || p.getActivo()) // Asumiendo null como activo por compatibilidad
                .map(ProductoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoResponseDTO> getProductoById(Integer id) {
        return productoRepository.findById(id)
                .filter(p -> p.getActivo() == null || p.getActivo())
                .map(ProductoResponseDTO::new);
    }

    @Override
    public ProductoResponseDTO saveProducto(ProductoRequestDTO productoDTO) {
        // Validación de duplicados
        if (productoRepository.existsByNombre(productoDTO.getNombre())) {
            throw new ValidationException("Ya existe un producto con el nombre: " + productoDTO.getNombre());
        }


        // Validaciones
        if (productoDTO.getNombre() == null || productoDTO.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre del producto es obligatorio y no puede estar vacío");
        }
        if (productoDTO.getCategoria() == null || productoDTO.getCategoria().trim().isEmpty()) {
            throw new ValidationException("La categoría es obligatoria y no puede estar vacía");
        }
        if (productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0) {
            throw new ValidationException("El precio es obligatorio y debe ser mayor a 0");
        }
        if (productoDTO.getStock() == null || productoDTO.getStock() < 0) {
            throw new ValidationException("El stock es obligatorio y no puede ser negativo");
        }

        // Buscar categoría
        Categoria categoria = categoriaRepository.findByNombre(productoDTO.getCategoria())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Categoría no encontrada: " + productoDTO.getCategoria()));

        // Crear producto
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setCategoria(categoria);
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setImagen(productoDTO.getImagen());
        producto.setActivo(true);

        Producto savedProducto = productoRepository.save(producto);
        return new ProductoResponseDTO(savedProducto);
    }

    @Override
    public ProductoResponseDTO updateProducto(Integer id, ProductoRequestDTO productoDTO) {
        Producto existingProducto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Validación de duplicados (excluyendo el actual)
        if (productoRepository.existsByNombreAndIdNot(productoDTO.getNombre(), id)) {
            throw new ValidationException("Ya existe otro producto con el nombre: " + productoDTO.getNombre());
        }
        // Validaciones
        if (productoDTO.getNombre() == null || productoDTO.getNombre().trim().isEmpty()) {
            throw new ValidationException("El nombre del producto es obligatorio y no puede estar vacío");
        }
        if (productoDTO.getCategoria() == null || productoDTO.getCategoria().trim().isEmpty()) {
            throw new ValidationException("La categoría es obligatoria y no puede estar vacía");
        }
        if (productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0) {
            throw new ValidationException("El precio es obligatorio y debe ser mayor a 0");
        }
        if (productoDTO.getStock() == null || productoDTO.getStock() < 0) {
            throw new ValidationException("El stock es obligatorio y no puede ser negativo");
        }

        // Buscar categoría
        Categoria categoria = categoriaRepository.findByNombre(productoDTO.getCategoria())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Categoría no encontrada: " + productoDTO.getCategoria()));

        // Actualizar producto
        existingProducto.setNombre(productoDTO.getNombre());
        existingProducto.setCategoria(categoria);
        existingProducto.setPrecio(productoDTO.getPrecio());
        existingProducto.setStock(productoDTO.getStock());
        existingProducto.setDescripcion(productoDTO.getDescripcion());
        existingProducto.setImagen(productoDTO.getImagen());
        // No cambiamos el estado activo aquí, eso es en delete o un endpoint específico

        Producto updatedProducto = productoRepository.save(existingProducto);
        return new ProductoResponseDTO(updatedProducto);
    }

    @Override
    public Optional<String> getProductNameById(Integer id) {
        return productoRepository.findById(id)
                .map(Producto::getNombre);
    }

    @Override
    public void deleteProducto(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Borrado lógico
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}

