package com.backend.huertohogar.service;

import java.util.List;
import java.util.Optional;

import com.backend.huertohogar.dto.UserRequestDTO;
import com.backend.huertohogar.dto.UserResponseDTO;

public interface UserService {
    List<UserResponseDTO> findAllUsers();
    
    List<UserResponseDTO> findAllUsersIncludingInactive();

    Optional<UserResponseDTO> findUserById(Integer id);

    UserResponseDTO saveUser(UserRequestDTO user);

    void deleteUser(Integer id);
    
    void reactivateUser(Integer id);

    UserResponseDTO updateUser(Integer id, UserRequestDTO user);

    Integer findIdByEmail(String email);
}
