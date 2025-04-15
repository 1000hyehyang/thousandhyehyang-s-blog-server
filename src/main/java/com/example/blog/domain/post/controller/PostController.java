package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.dto.PagedPostResponse;
import com.example.blog.domain.post.dto.PostRequest;
import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.service.PostService;
import com.example.blog.global.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostRequest request) {
        return ApiResponse.success(postService.create(request));
    }

    @GetMapping("/paged")
    public ApiResponse<PagedPostResponse> getPagedPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String category
    ) {
        return ApiResponse.success(postService.getPagedPostsResponse(page, size, category));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPostById(@PathVariable Long id) {
        return ApiResponse.success(postService.getById(id));
    }
}