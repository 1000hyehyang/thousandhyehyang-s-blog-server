package com.example.blog.domain.post.dto;

public record PostRequest(
        String title,
        String category,
        String content,
        String tags,
        String thumbnailUrl
) {}