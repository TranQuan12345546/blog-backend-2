package com.example.blog.controller;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.Blog;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;

    // Danh sách tất cả bài viết
    @GetMapping("/admin/blogs")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<BlogPublic> pageInfo = blogService.getAllBlog(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/blog/blog-index";
    }

    // Danh sách bài viết của tôi
    @GetMapping("/admin/blogs/own-blogs")
    public String getOwnBlogPage(Model model) {
        List<BlogPublic> blogList = blogService.getAllOwnBlog();
        model.addAttribute("blogList", blogList);
        return "admin/blog/own-blog";
    }

    // Tạo bài viết
    @GetMapping("/admin/blogs/create")
    public String getBlogCreatePage(Model model) {
        return "admin/blog/blog-create";
    }

    // Chi tiết bài viết
    @GetMapping("/admin/blogs/{id}/detail")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "admin/blog/blog-detail";
    }

    @PostMapping("api/v1/admin/blogs")
    public ResponseEntity<?> addBlog(@RequestBody UpsertBlogRequest upsertBlogRequest) {
        Blog blog = blogService.addBlog(upsertBlogRequest);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("api/v1/admin/blogs/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody UpsertBlogRequest upsertBlogRequest) {
        Blog blog = blogService.updateBlog(id, upsertBlogRequest);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("api/v1/admin/blogs/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable int id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Delete Success");
    }
}
