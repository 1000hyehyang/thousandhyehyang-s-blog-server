package com.example.blog.domain.comment.dto;

public record CommentRequest(
        String nickname,
        String emoji,
        String content
) {}
