package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.RolResponseDTO;
import com.backend.huertohogar.model.Rol;
import com.backend.huertohogar.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<RolResponseDTO> findAllRol() {
        return rolRepository.findAll().stream()
                .map(RolResponseDTO::new)
                .collect(Collectors.toList());
    }
}
