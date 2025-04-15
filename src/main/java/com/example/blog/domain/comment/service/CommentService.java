package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.dto.CommentRequest;
import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponse> getCommentsByPost(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId).stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        c.getNickname(),
                        c.getEmoji(),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .toList();
    }

    public CommentResponse addComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다: id = " + postId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setNickname(request.nickname());
        comment.setEmoji(request.emoji());
        comment.setContent(request.content());

        Comment saved = commentRepository.save(comment);

        return new CommentResponse(
                saved.getId(),
                saved.getNickname(),
                saved.getEmoji(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }
}
