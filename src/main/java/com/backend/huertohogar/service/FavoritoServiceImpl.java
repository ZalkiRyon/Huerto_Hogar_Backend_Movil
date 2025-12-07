package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.FavoritoResponseDTO;
import com.backend.huertohogar.exception.ResourceNotFoundException;
import com.backend.huertohogar.model.Favorito;
import com.backend.huertohogar.model.Producto;
import com.backend.huertohogar.model.User;
import com.backend.huertohogar.repository.FavoritoRepository;
import com.backend.huertohogar.repository.ProductoRepository;
import com.backend.huertohogar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritoServiceImpl implements FavoritoService {
    private final FavoritoRepository favoritoRepository;
    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public FavoritoServiceImpl(FavoritoRepository favoritoRepository, UserRepository userRepository, ProductoRepository productoRepository) {
        this.favoritoRepository = favoritoRepository;
        this.userRepository = userRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoritoResponseDTO> getFavoritosByUsuarioId(Integer usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId).stream()
                .map(FavoritoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FavoritoResponseDTO addFavorito(Integer usuarioId, Integer productoId) {
        // 1. Verificar si ya existe (usando la Unique Constraint del repo)
        if (favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId).isPresent()) {
            throw new RuntimeException("Este producto ya está en la lista de favoritos.");
        }

        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));


        Favorito nuevoFavorito = new Favorito(user, producto);
        Favorito savedFavorito = favoritoRepository.save(nuevoFavorito);

        return new FavoritoResponseDTO(savedFavorito);
    }

    @Override
    @Transactional
    public void removeFavorito(Integer usuarioId, Integer productoId) {
        favoritoRepository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isFavorito(Integer usuarioId, Integer productoId) {
        return favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId).isPresent();
    }

}
