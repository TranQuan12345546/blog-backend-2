package com.example.blog.controller;

import com.example.blog.entity.Category;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CategoryController {
    // Danh sách tất cả bài viết
    private CategoryService categoryService;
    // Danh sách tất cả bài viết
    @GetMapping("/admin/categories")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                              Model model) {
        Page<Category> categoryPage = categoryService.getAllCategoryPage(page, pageSize);
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("currentPage", page);
        return "admin/category/category-list";
    }


    @PostMapping("/admin/categories/create")
    public ResponseEntity<?> addCategory(@RequestParam String name) {
        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok("Thành công");
    }

    @PutMapping("/api/v1/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestParam String name, @PathVariable int id) {
        Category category = categoryService.updateCategory(name, id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @DeleteMapping("/api/v1/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete success");
    }
}
