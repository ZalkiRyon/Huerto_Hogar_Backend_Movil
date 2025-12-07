package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.FavoritoResponseDTO;

import java.util.List;

public interface FavoritoService {

    List<FavoritoResponseDTO> getFavoritosByUsuarioId(Integer usuarioId);

    FavoritoResponseDTO addFavorito(Integer usuarioId, Integer productoId);
    void removeFavorito(Integer usuarioId, Integer productoId);

    boolean isFavorito(Integer usuarioId, Integer productoId);
}
