package com.example.blog.domain.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String nickname,
        String emoji,
        String content,
        LocalDateTime createdAt
) {}
