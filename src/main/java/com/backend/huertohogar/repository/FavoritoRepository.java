package com.backend.huertohogar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.huertohogar.model.Favorito;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
    
    // Get all favorites for a user
    List<Favorito> findByUsuarioId(Integer usuarioId);
    
    // Get favorites only for active users and active products
    @Query("SELECT f FROM Favorito f JOIN f.usuario u JOIN f.producto p " +
           "WHERE u.id = :usuarioId AND u.activo = true AND p.activo = true")
    List<Favorito> findActiveByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // Find specific favorite
    Optional<Favorito> findByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);

    // Delete specific favorite
    @Modifying
    void deleteByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);
    
    // Delete all favorites for a product (used when deactivating product)
    @Modifying
    @Query("DELETE FROM Favorito f WHERE f.producto.id = :productoId")
    void deleteByProductoId(@Param("productoId") Integer productoId);
    
    // Check if favorite exists
    boolean existsByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);
    
    // Count favorites for a product
    long countByProductoId(Integer productoId);
}
