package com.hemanth.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "posts",
        indexes = @Index(columnList = "slug"))
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Post title is required")
    private String title;

    @Lob
    @Nullable
    private String body;

    @Column(unique = true)
    @NotBlank
    private String identifier;

    @NotBlank
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subredditId", nullable = false)
    private Subreddit subreddit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Instant createdAt;

    private Instant updatedAt;
}
