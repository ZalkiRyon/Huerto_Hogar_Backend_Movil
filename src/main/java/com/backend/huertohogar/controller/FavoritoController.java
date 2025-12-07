package com.backend.huertohogar.controller;

import com.backend.huertohogar.dto.FavoritoResponseDTO;
import com.backend.huertohogar.exception.ResourceNotFoundException;
import com.backend.huertohogar.service.FavoritoService;
import com.backend.huertohogar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "*")
public class FavoritoController {
    private final FavoritoService favoritoService;
    private final UserService userService;

    @Autowired
    public FavoritoController(FavoritoService favoritoService, UserService userService) {
        this.favoritoService = favoritoService;
        this.userService = userService;
    }

    private Integer getAuthenticatedUserId(Authentication authentication) {
        String userEmail = authentication.getName();

        try {
            return userService.findIdByEmail(userEmail);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Error de autenticación: El usuario asociado al token no existe.");
        }
    }

    @GetMapping
    public ResponseEntity<List<FavoritoResponseDTO>> getMyFavorites(Authentication authentication) {
        Integer userId = getAuthenticatedUserId(authentication);
        List<FavoritoResponseDTO> favoritos = favoritoService.getFavoritosByUsuarioId(userId);
        return new ResponseEntity<>(favoritos, HttpStatus.OK);
    }

    @PostMapping("/{productoId}")
    public ResponseEntity<FavoritoResponseDTO> addFavorite(
            @PathVariable Integer productoId,
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);
        FavoritoResponseDTO nuevoFavorito = favoritoService.addFavorito(userId, productoId);

        return new ResponseEntity<>(nuevoFavorito, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Integer productoId,
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);
        favoritoService.removeFavorito(userId, productoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/check/{productoId}")
    public ResponseEntity<Boolean> isFavorite(
            @PathVariable Integer productoId,
            Authentication authentication) {

        Integer userId = getAuthenticatedUserId(authentication);
        boolean isFav = favoritoService.isFavorito(userId, productoId);

        return new ResponseEntity<>(isFav, HttpStatus.OK);
    }
}
