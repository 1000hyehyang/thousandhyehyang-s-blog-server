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

    @GetMapping
    public ApiResponse<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ApiResponse.success(commentService.getCommentsByPost(postId));
    }

    @PostMapping
    public ApiResponse<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        return ApiResponse.success(commentService.addComment(postId, request));
    }
}