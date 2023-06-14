package com.example.blog.repository;

import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIdIn(List<Integer> ids);

    Category findByName(String name);
    Page<Category> findAll(Pageable pageable);
}