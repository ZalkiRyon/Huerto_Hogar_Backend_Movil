package com.backend.huertohogar.controller;

import com.backend.huertohogar.dto.UserRequestDTO;
import com.backend.huertohogar.dto.UserResponseDTO;
import com.backend.huertohogar.service.FileStorageService;
import com.backend.huertohogar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public UserController(UserService userService, FileStorageService fileStorageService) {
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
        // code 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                // code 200
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // code 404
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDto) {
        UserResponseDTO newUserResponse = userService.saveUser(userDto);
        return new ResponseEntity<>(newUserResponse, HttpStatus.CREATED);
        // code 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @RequestBody UserRequestDTO userDto) {

        UserResponseDTO updatedUserResponse = userService.updateUser(id, userDto);

        return new ResponseEntity<>(updatedUserResponse, HttpStatus.OK);
        // code 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {

        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // code 204
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload-foto")
    public ResponseEntity<String> uploadFotoPerfil(@RequestParam("file") MultipartFile file, Authentication authentication) {

        // Obtener la ID del usuario autenticado (usando el helper que ya creaste)
        Integer userId = userService.findIdByEmail(authentication.getName());

        // Línea 2: Guardar el archivo físicamente (requiere Disco/Permisos)
        String fileName = fileStorageService.storeFile(file);

        // Línea 3: Actualizar la entidad User con el nombre del archivo (requiere DB)
        userService.updateFotoPerfil(userId, fileName);

        return new ResponseEntity<>("Foto de perfil subida y actualizada. Nombre: " + fileName, HttpStatus.OK);
    }
}
