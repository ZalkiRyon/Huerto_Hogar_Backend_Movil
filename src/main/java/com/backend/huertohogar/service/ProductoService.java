package com.backend.huertohogar.service;

import java.util.List;
import java.util.Optional;

import com.backend.huertohogar.dto.ProductoRequestDTO;
import com.backend.huertohogar.dto.ProductoResponseDTO;

public interface ProductoService {
    List<ProductoResponseDTO> getAllProductos();

    List<ProductoResponseDTO> getAllProductosIncludingInactive();

    List<ProductoResponseDTO> getProductosByCategory(String category);

    Optional<ProductoResponseDTO> getProductoById(Integer id);

    ProductoResponseDTO saveProducto(ProductoRequestDTO productoDTO);

    void deleteProducto(Integer id);

    ProductoResponseDTO updateProducto(Integer id, ProductoRequestDTO productoDTO);

    Optional<String> getProductNameById(Integer id);

    void reactivateProducto(Integer id);

    void updateProductImage(Integer id, String imageUrl);
}
