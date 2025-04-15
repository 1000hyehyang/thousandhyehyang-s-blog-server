package com.example.blog.domain.post.dto;

import java.util.List;

public record PagedPostResponse(
        List<PostResponse> content,
        int currentPage,
        int totalPages,
        long totalElements
) {}
