package com.example.blog.domain.post.service;

import com.example.blog.domain.post.dto.PostRequest;
import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 글 작성
    public Long create(PostRequest request) {
        Post post = new Post(
                request.title(),
                request.category(),
                request.content(),
                request.tags(),
                request.thumbnailUrl()
        );
        Post saved = postRepository.save(post);
        return saved.getId();
    }

    // 페이지네이션 글 목록 조회
    public Page<PostResponse> getPagedPosts(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> pageResult;

        if (category == null || category.equals("전체")) {
            pageResult = postRepository.findAll(pageable);
        } else {
            pageResult = postRepository.findByCategory(category, pageable);
        }

        return pageResult.map(this::toResponse);
    }


    // 단일 글 조회
    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다: id = " + id));
        return toResponse(post);
    }

    private PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent(),
                post.getTags(),
                post.getThumbnailUrl(),
                post.getCreatedAt()
        );
    }
}
