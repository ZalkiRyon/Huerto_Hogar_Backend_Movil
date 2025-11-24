package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.RolResponseDTO;
import java.util.List;

public interface RolService {
    List<RolResponseDTO> findAllRol();
}
