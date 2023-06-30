package com.example.blog.service;

import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import com.example.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<Category> getAllCategoryPage(int page, int pageSize) {
        Page<Category> categoryList = categoryRepository.findAll(PageRequest.of(page - 1, pageSize));
        return categoryList;
    }

    public List<CategoryPublic> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> CategoryPublic.of(category))
                .collect(Collectors.toList());
    }

    public Category addCategory(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            throw new RuntimeException("Category existed");
        }
        Category category1 = new Category();
        category1.setName(name);
        categoryRepository.save(category1);
        return category1;
    }

    public Category updateCategory(String name, int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Not found file");
        });
        if (name.equals(category.getName())) {
            return null;
        }
        System.out.println(category.getName());;
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

}
