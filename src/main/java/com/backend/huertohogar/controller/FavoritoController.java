package com.backend.huertohogar.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.huertohogar.dto.FavoritoResponseDTO;
import com.backend.huertohogar.service.FavoritoService;

/**
 * FavoritoController - Manages user favorites
 * Endpoints: GET (all favorites), POST (add), PUT (update - not typically used), DELETE (remove)
 * Security: DISABLED - userId passed as parameter
 */
@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "*")
public class FavoritoController {
    private final FavoritoService favoritoService;

    @Autowired
    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    /**
     * GET /api/favoritos/usuario/{usuarioId} - Get all favorites for a user
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoResponseDTO>> getUserFavorites(@PathVariable Integer usuarioId) {
        List<FavoritoResponseDTO> favoritos = favoritoService.getFavoritosByUsuarioId(usuarioId);
        return new ResponseEntity<>(favoritos, HttpStatus.OK);
    }

    /**
     * POST /api/favoritos - Add a favorite
     * Body: { "usuarioId": 1, "productoId": 5 }
     */
    @PostMapping
    public ResponseEntity<FavoritoResponseDTO> addFavorite(@RequestBody Map<String, Integer> request) {
        Integer usuarioId = request.get("usuarioId");
        Integer productoId = request.get("productoId");
        
        if (usuarioId == null || productoId == null) {
            throw new IllegalArgumentException("usuarioId and productoId are required");
        }
        
        FavoritoResponseDTO nuevoFavorito = favoritoService.addFavorito(usuarioId, productoId);
        return new ResponseEntity<>(nuevoFavorito, HttpStatus.CREATED);
    }

    /**
     * DELETE /api/favoritos - Remove a favorite
     * Body: { "usuarioId": 1, "productoId": 5 }
     */
    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestBody Map<String, Integer> request) {
        Integer usuarioId = request.get("usuarioId");
        Integer productoId = request.get("productoId");
        
        if (usuarioId == null || productoId == null) {
            throw new IllegalArgumentException("usuarioId and productoId are required");
        }
        
        favoritoService.removeFavorito(usuarioId, productoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE /api/favoritos/{favoritoId} - Remove favorite by ID
     */
    @DeleteMapping("/{favoritoId}")
    public ResponseEntity<Void> removeFavoriteById(@PathVariable Integer favoritoId) {
        // Implementation would need a new service method
        throw new UnsupportedOperationException("Delete by ID not implemented yet");
    }

    /**
     * GET /api/favoritos/check - Check if a product is favorited by user
     * Params: ?usuarioId=1&productoId=5
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> isFavorite(
            @RequestParam Integer usuarioId,
            @RequestParam Integer productoId) {
        
        boolean isFav = favoritoService.isFavorito(usuarioId, productoId);
        return new ResponseEntity<>(isFav, HttpStatus.OK);
    }
}
