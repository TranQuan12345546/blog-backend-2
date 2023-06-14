package com.example.blog.dto;

import java.util.List;

public class UpsertBlogRequest {
    public class UpsertBlogRequest {
        private String title;
        private String description;
        private String content;
        private String thumbnail;
        private Boolean status;
        private List<Integer> categoryIds; // Danh sách id của các category áp dụng
    }
}
