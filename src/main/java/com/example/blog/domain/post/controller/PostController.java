package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.dto.PostRequest;
import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 글 작성
    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostRequest request) {
        Long id = postService.create(request);
        return ResponseEntity.ok(id);
    }

    // 페이지네이션 글 목록 조회
    @GetMapping("/paged")
    public ResponseEntity<?> getPagedPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String category
    ) {
        Page<PostResponse> result = postService.getPagedPosts(page, size, category);

        Map<String, Object> response = new HashMap<>();
        response.put("content", result.getContent());
        response.put("currentPage", result.getNumber() + 1); // 0-based
        response.put("totalPages", result.getTotalPages());
        response.put("totalElements", result.getTotalElements());

        return ResponseEntity.ok(response);
    }

    // 단일 글 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.getById(id);
        return ResponseEntity.ok(post);
    }
}
