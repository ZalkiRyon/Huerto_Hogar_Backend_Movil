package com.backend.huertohogar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.huertohogar.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByRun(String run);
    
    // Soft delete support
    List<User> findByActivoTrue();
    
    Optional<User> findByIdAndActivoTrue(Integer id);
}
