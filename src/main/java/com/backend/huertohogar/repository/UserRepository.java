package com.backend.huertohogar.repository;

import com.backend.huertohogar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    List<User> findByNombreAndApellido(String nombre, String apellido);
}
