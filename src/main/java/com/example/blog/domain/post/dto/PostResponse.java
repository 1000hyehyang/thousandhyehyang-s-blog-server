package com.example.blog.domain.post.dto;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String category,
        String content,
        String tags,
        String thumbnailUrl,
        LocalDateTime createdAt
) {}
