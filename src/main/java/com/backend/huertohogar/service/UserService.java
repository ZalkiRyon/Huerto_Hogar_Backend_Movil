package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.UserRequestDTO;
import com.backend.huertohogar.dto.UserResponseDTO;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> findAllUsers();

    Optional<UserResponseDTO> findUserById(Integer id);

    UserResponseDTO saveUser(UserRequestDTO user);

    void deleteUser(Integer id);

    UserResponseDTO updateUser(Integer id, UserRequestDTO user);

    Integer findIdByEmail(String email);

    void updateFotoPerfil(Integer userId, String fileName);
}
