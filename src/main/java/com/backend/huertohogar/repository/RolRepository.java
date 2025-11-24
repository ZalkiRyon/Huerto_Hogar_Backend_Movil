package com.backend.huertohogar.repository;

import com.backend.huertohogar.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
}
