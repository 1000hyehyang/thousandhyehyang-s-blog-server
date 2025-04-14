package com.example.blog.domain.post.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String tags;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    protected Post() {} // JPA 기본 생성자

    public Post(String title, String category, String content, String tags, String thumbnailUrl) {
        this.title = title;
        this.category = category;
        this.content = content;
        this.tags = tags;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getContent() { return content; }
    public String getTags() { return tags; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
