package com.example.blog.controller;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Category;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping("admin/categories")
    public String getAllCategory(@RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "10") int pageSize,
                                 Model model) {
        Page<CategoryPublic> pageInfo = categoryService.getAllCategory(page, pageSize);
        model.addAttribute("page", pageInfo);
        return "/admin/category/category-list";
    }

    @PostMapping("api/v1/admin/categories")
    public ResponseEntity<?> addCategory(@RequestParam String name) {
        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok(category);
    }

    @PutMapping("api/v1/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@RequestParam String name, @PathVariable int id) {
        Category category = categoryService.updateCategory(name, id);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("api/v1/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete success");
    }
}
