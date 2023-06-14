package com.example.blog.service;

import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import com.example.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public Category addCategory(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            throw new RuntimeException("Category existed");
        }
        category.setName(name);
        categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(String name, int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not found file");
        });
        if (name == category.getName()) {
            throw new RuntimeException("Name must not be the same");
        }
        category.setName(name);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not found file");
        });
        categoryRepository.delete(category);
    }

    public Page<Category> getAllCategory(int page, int pageSize) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page, pageSize));
        return categories;
    }
}
