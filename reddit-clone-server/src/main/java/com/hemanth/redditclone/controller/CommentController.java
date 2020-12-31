package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.CommentRequest;
import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(commentRequest));
    }

    @GetMapping("by-postId/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable(value = "id") Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentsByPost(postId));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable(value = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentsByUser(username));
    }
}
