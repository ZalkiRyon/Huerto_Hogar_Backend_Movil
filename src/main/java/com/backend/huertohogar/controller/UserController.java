package com.backend.huertohogar.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.huertohogar.dto.UserRequestDTO;
import com.backend.huertohogar.dto.UserResponseDTO;
import com.backend.huertohogar.model.User;
import com.backend.huertohogar.repository.UserRepository;
import com.backend.huertohogar.service.CloudinaryService;
import com.backend.huertohogar.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
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

    /**
     * Login simple sin autenticación (solo para desarrollo)
     * POST /api/usuarios/login
     * Body: { "email": "usuario@duocuc.cl", "password": "password123" }
     * Response: { "user": UserResponseDTO, "token": "fake-token", "message": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        if (email == null || email.trim().isEmpty()) {
            response.put("message", "Email es requerido");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (password == null || password.trim().isEmpty()) {
            response.put("message", "Contraseña es requerida");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            // Buscar usuario por email
            User user = userRepository.findByEmail(email.trim().toLowerCase());
            
            if (user == null) {
                response.put("message", "Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            // Verificar que el usuario esté activo
            if (!user.getActivo()) {
                response.put("message", "Usuario inactivo");
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }
            
            // Verificar contraseña (texto plano, solo para desarrollo)
            if (!password.equals(user.getPassword())) {
                response.put("message", "Contraseña incorrecta");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            
            // Convertir a DTO
            UserResponseDTO userDto = userService.findUserById(user.getId()).orElse(null);
            
            // Login exitoso - sin JWT real por ahora
            response.put("user", userDto);
            response.put("token", "fake-token-dev-mode");
            response.put("message", "Login exitoso");
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            response.put("message", "Error al procesar login: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Registro simple sin autenticación (solo para desarrollo)
     * POST /api/usuarios/register
     * Body: UserRequestDTO
     * Response: { "user": UserResponseDTO, "token": "fake-token", "message": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRequestDTO userDto) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Forzar role_id a 2 (cliente) para registro público
            userDto.setRole_id(2);
            
            // Validar email duplicado
            String email = userDto.getEmail();
            if (email != null) {
                User existingUser = userRepository.findByEmail(email.trim().toLowerCase());
                if (existingUser != null) {
                    response.put("message", "El email ya está registrado");
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                }
            }
            
            // Crear usuario
            UserResponseDTO newUser = userService.saveUser(userDto);
            
            response.put("user", newUser);
            response.put("token", "fake-token-dev-mode");
            response.put("message", "Usuario registrado exitosamente");
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            response.put("message", "Error al registrar usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Obtiene todos los usuarios incluyendo inactivos (solo admin)
     * GET /api/usuarios/todos
     */
    @GetMapping("/todos")
    public ResponseEntity<List<UserResponseDTO>> getAllUsersIncludingInactive() {
        List<UserResponseDTO> users = userService.findAllUsersIncludingInactive();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    /**
     * Reactiva un usuario desactivado (solo admin)
     * PATCH /api/usuarios/{id}/reactivar
     */
    @org.springframework.web.bind.annotation.PatchMapping("/{id}/reactivar")
    public ResponseEntity<Void> reactivateUser(@PathVariable Integer id) {
        userService.reactivateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * Sube imagen de perfil de usuario a Cloudinary
     * POST /api/usuarios/{id}/foto-perfil
     * @param id ID del usuario
     * @param file Archivo de imagen (MultipartFile)
     * @return Usuario actualizado con URL de Cloudinary
     */
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Subir foto de perfil",
        description = "Sube una imagen de perfil a Cloudinary y actualiza el usuario con la URL"
    )
    @PostMapping(value = "/{id}/foto-perfil", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadProfileImage(
            @PathVariable Integer id,
            @io.swagger.v3.oas.annotations.Parameter(description = "Archivo de imagen JPG/PNG", required = true)
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                response.put("message", "El archivo está vacío");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Validar tipo de archivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("message", "El archivo debe ser una imagen");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Subir imagen a Cloudinary (carpeta "huerto_hogar/profiles")
            String imageUrl = cloudinaryService.uploadImage(file, "huerto_hogar/profiles");
            
            // Actualizar usuario con la nueva URL
            UserResponseDTO updatedUser = userService.updateProfileImage(id, imageUrl);
            
            response.put("message", "Foto de perfil actualizada exitosamente");
            response.put("user", updatedUser);
            response.put("imageUrl", imageUrl);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            
        } catch (IOException e) {
            response.put("message", "Error al subir la imagen: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            
        } catch (Exception e) {
            response.put("message", "Error inesperado: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
