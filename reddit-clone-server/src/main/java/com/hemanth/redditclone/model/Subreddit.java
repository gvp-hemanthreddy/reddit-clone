package com.hemanth.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(name = "subreddits")
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Subreddit name is required")
    private String name;

    @NotBlank(message = "Subreddit title is required")
    private String title;

    @Lob
    @Nullable
    private String description;

    @Nullable
    private String imageUrn;

    @Nullable
    private String bannerUrn;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
//    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Instant createdAt;

    private Instant updatedAt;
}
