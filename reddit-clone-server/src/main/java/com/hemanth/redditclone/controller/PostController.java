package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.CommentRequest;
import com.hemanth.redditclone.dto.PostRequest;
import com.hemanth.redditclone.service.CommentService;
import com.hemanth.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(postRequest));
    }

    @GetMapping
    public ResponseEntity getAllPosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAllPosts());
    }

    @GetMapping("{identifier}/{slug}")
    public ResponseEntity getPostByIdentifier(@PathVariable(value = "identifier") String identifier) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPostByIdentifier(identifier));
    }

    @PostMapping("{identifier}/{slug}/comments")
    public ResponseEntity createCommentsOnPost(@PathVariable(value = "identifier") String identifier, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.createCommentOnPost(identifier, commentRequest));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity getPostsBySubreddit(@PathVariable(value = "id") Long subredditId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPostsBySubreddit(subredditId));
    }

    @GetMapping("by-username/{username}")
    public ResponseEntity getPostsByUsername(@PathVariable(value = "username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPostsByUsername(username));
    }
}
