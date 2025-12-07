package com.backend.huertohogar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.huertohogar.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Integer id);
    
    // Soft delete support
    List<Producto> findByActivoTrue();
    
    Optional<Producto> findByIdAndActivoTrue(Integer id);
}
