package com.example.blog.service;

import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.UpsertBlogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    private List<Category> toListCategories(List<Integer> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (int i : categoryIds) {
            Category category = categoryRepository.findById(i).orElseThrow(() -> {
                throw new RuntimeException("User not found");
            });
            categories.add(category);
        }
        return categories;
    }

    public Page<BlogPublic> getAllBlog(Integer page, Integer pageSize) {
        Page<BlogPublic> pageInfo = blogRepository.findBlogs(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        return pageInfo;
    }

    public Page<BlogPublic> getAllOwnBlog(Integer page, Integer pageSize) {
        // TODO : Hiện tại fix userId. Sau này userId chính là user đang login
        Integer userId = 1;

        Page<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(
                userId,
                PageRequest.of(page - 1, pageSize)
        );

        return pageInfo;
    }

    public List<BlogPublic> getAllOwnBlog() {
        // TODO : Hiện tại fix userId. Sau này userId chính là user đang login
        Integer userId = 1;

        List<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(userId);
        return pageInfo;
    }

    public Blog getBlogById(Integer id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Blog not found");
        });
        return blog;
    }

    public Blog addBlog(UpsertBlogRequest upsertBlogRequest) {
        Blog blog = new Blog();
        addDataToBlog(blog, upsertBlogRequest);
        blogRepository.save(blog);
        return blog;
    }

    private void addDataToBlog(Blog blog, UpsertBlogRequest upsertBlogRequest) {
        blog.setTitle(upsertBlogRequest.getTitle());
        blog.setDescription(upsertBlogRequest.getDescription());
        blog.setContent(upsertBlogRequest.getContent());
        blog.setThumbnail(upsertBlogRequest.getThumbnail());
        blog.setStatus(upsertBlogRequest.getStatus());
        blog.setCategories(toListCategories(upsertBlogRequest.getCategoryIds()));
    }

    public Blog updateBlog(int id, UpsertBlogRequest upsertBlogRequest) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("User not found");
        });
        addDataToBlog(blog, upsertBlogRequest);
        blogRepository.save(blog);
        return blog;
    }

    public Boolean deleteBlog(int id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("ID not found");
        });
        blogRepository.delete(blog);
        return true;
    }
}
