package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.BlogResponseDTO;

import java.util.List;

public interface BlogService {
    List<BlogResponseDTO> findAllBlogs();
}
