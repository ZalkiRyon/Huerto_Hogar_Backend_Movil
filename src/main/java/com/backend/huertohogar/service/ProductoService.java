package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.ProductoRequestDTO;
import com.backend.huertohogar.dto.ProductoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoResponseDTO> getAllProductos();

    Optional<ProductoResponseDTO> getProductoById(Integer id);

    ProductoResponseDTO saveProducto(ProductoRequestDTO productoDTO);

    void deleteProducto(Integer id);

    ProductoResponseDTO updateProducto(Integer id, ProductoRequestDTO productoDTO);

    Optional<String> getProductNameById(Integer id);
}
