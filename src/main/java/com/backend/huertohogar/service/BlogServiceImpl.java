package com.backend.huertohogar.service;

import com.backend.huertohogar.dto.BlogResponseDTO;
import com.backend.huertohogar.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<BlogResponseDTO> findAllBlogs() {
        return blogRepository.findAll().stream()
                .map(BlogResponseDTO::new)
                .collect(Collectors.toList());
    }
}
