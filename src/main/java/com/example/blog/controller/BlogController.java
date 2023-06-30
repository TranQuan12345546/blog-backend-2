package com.example.blog.controller;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.TokenConfirm;
import com.example.blog.entity.User;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.TokenConfirmRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.ChangePassword;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BlogController {

    @Autowired
    private PasswordEncoder encoder;
    private final BlogService blogService;
    private final CategoryService categoryService;
    private TokenConfirmRepository tokenConfirmRepository;
    private final UserRepository userRepository;

    @GetMapping("/admin")
    public String getHome() {
        return "security/index";
    }

    @GetMapping("/login")
    public String getLoginPage(Authentication authentication) {
        if(authentication != null) {
            return "redirect:/";
        }
        return "security/login";
    }

    @GetMapping("/forgot-password")
    public String getForgotPassword() {

        return "security/forgot-password";
    }

    @GetMapping("/doi-mat-khau/{token}")
    public String getUpdatePasswordPage(@PathVariable String token, Model model) {
        // Kiểm tra token có hợp lệ hay không
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository.findByToken(token);
        if(optionalTokenConfirm.isEmpty()) {
            model.addAttribute("isValid", false);
            model.addAttribute("message", "Token không hợp lệ");
            return "security/update-password";
        }
        // Kiểm tra token đã được kích hoạt hay chưa
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if(tokenConfirm.getConfirmedAt() != null) {
            model.addAttribute("isValid", false);
            model.addAttribute("message", "Token đã được kích hoạt");
            return "security/update-password";
        }

        // Kiểm tra token đã hết hạn hay chưa
        if(tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            model.addAttribute("isValid", false);
            model.addAttribute("message", "Token đã hết hạn");
            return "security/update-password";
        }

        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        model.addAttribute("isValid", true);
        model.addAttribute("token", token);
        return "security/update-password";
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
        if (!changePassword.getPassword().equals(changePassword.getConfirmPassword()) ) {
            throw new RuntimeException("Password phải trùng với confirmPassword");
        }
        TokenConfirm tokenConfirm = tokenConfirmRepository.findByToken(changePassword.getToken())
                .orElseThrow(() -> {
                    throw new NotFoundException("Token not found");
                });

        User user = tokenConfirm.getUser();
        user.setPassword(encoder.encode(changePassword.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    // Danh sách tất cả bài viết
    @GetMapping("/admin/blogs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    public String getOwnBlogPage(Model model) {
        List<BlogPublic> blogList = blogService.getAllOwnBlog();
        model.addAttribute("blogList", blogList);
        return "admin/blog/own-blog";
    }

    // Tạo bài viết
    @GetMapping("/admin/blogs/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    public String getBlogCreatePage(Model model) {
        List<CategoryPublic> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "admin/blog/blog-create";
    }

    // Chi tiết bài viết
    @GetMapping("/admin/blogs/{id}/detail")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        BlogPublic blog = blogService.getBlogById(id);
        List<CategoryPublic> categoryList = categoryService.getAllCategory();

        model.addAttribute("blog", blog);
        model.addAttribute("categoryList", categoryList);
        return "admin/blog/blog-detail";
    }

    // Danh sách API
    // 1. Tạo bài viết
    @PostMapping("/api/v1/admin/blogs")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR') ")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request) {
        return new ResponseEntity<>(blogService.createBlog(request), HttpStatus.CREATED); // 201
    }

    // 2. Cập nhật bài viết
    @PutMapping("/api/v1/admin/blogs/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody UpsertBlogRequest request) {
        return ResponseEntity.ok(blogService.updateBlog(id, request)); // 200
    }

    // 3. Xóa bài viết
    @DeleteMapping("/api/v1/admin/blogs/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')" )
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build(); // 204
    }

}
