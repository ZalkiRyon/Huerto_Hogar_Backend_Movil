package com.backend.huertohogar.repository;

import com.backend.huertohogar.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
