package com.backend.huertohogar.repository;

import com.backend.huertohogar.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByUsuarioId(Integer usuarioId);
}
