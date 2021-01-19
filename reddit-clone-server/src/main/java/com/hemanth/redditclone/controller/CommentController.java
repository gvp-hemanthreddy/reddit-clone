package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.CommentResponse;
import com.hemanth.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable(value = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getCommentsByUser(username));
    }
}
