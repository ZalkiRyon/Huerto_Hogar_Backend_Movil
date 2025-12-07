package com.backend.huertohogar.repository;

import com.backend.huertohogar.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito,Integer> {
    List<Favorito> findByUsuarioId(Integer usuarioId);

    Optional<Favorito> findByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);

    // en vdd no se si usaremos esto, por siaca
    void deleteByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);
}
