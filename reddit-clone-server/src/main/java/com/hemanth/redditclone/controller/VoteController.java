package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.VoteRequest;
import com.hemanth.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/votes")
@AllArgsConstructor
public class VoteController {
    private VoteService voteService;

    @PostMapping
    public ResponseEntity vote(@RequestBody VoteRequest voteRequest) {
        voteService.vote(voteRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Voted successfully");
    }
}
