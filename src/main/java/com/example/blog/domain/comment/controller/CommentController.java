package com.example.blog.domain.comment.controller;

import com.example.blog.domain.comment.dto.CommentRequest;
import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/posts/{postId}/comments
    @GetMapping
    public ApiResponse<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPost(postId);
        return ApiResponse.success(comments);
    }

    // POST /api/posts/{postId}/comments
    @PostMapping
    public ApiResponse<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        CommentResponse saved = commentService.addComment(postId, request);
        return ApiResponse.success(saved);
    }
}
