package com.backend.huertohogar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.huertohogar.dto.FavoritoResponseDTO;
import com.backend.huertohogar.exception.ResourceNotFoundException;
import com.backend.huertohogar.model.Favorito;
import com.backend.huertohogar.model.Producto;
import com.backend.huertohogar.model.User;
import com.backend.huertohogar.repository.FavoritoRepository;
import com.backend.huertohogar.repository.ProductoRepository;
import com.backend.huertohogar.repository.UserRepository;

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
        // Get favorites with fresh product data from database
        // The JOIN FETCH ensures we get current product data, not cached snapshots
        List<Favorito> favoritos = favoritoRepository.findActiveByUsuarioId(usuarioId);
        
        return favoritos.stream()
                .map(favorito -> {
                    // Force refresh of product data to ensure we have latest values
                    Producto producto = productoRepository.findById(favorito.getProducto().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
                    
                    // Create a temporary favorito with fresh product data
                    Favorito favoritoFresco = new Favorito(favorito.getUsuario(), producto);
                    favoritoFresco.setId(favorito.getId());
                    
                    return new FavoritoResponseDTO(favoritoFresco);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FavoritoResponseDTO addFavorito(Integer usuarioId, Integer productoId) {
        // 1. Validate user is active
        User user = userRepository.findByIdAndActivoTrue(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado o inactivo con ID: " + usuarioId));
        
        // 2. Validate product is active
        Producto producto = productoRepository.findByIdAndActivoTrue(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado o inactivo con ID: " + productoId));
        
        // 3. Check if already exists
        if (favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId)) {
            throw new RuntimeException("Este producto ya está en la lista de favoritos.");
        }

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
