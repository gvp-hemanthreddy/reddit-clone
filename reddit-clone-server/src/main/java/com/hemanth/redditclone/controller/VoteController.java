package com.hemanth.redditclone.controller;

import com.hemanth.redditclone.dto.VoteDto;
import com.hemanth.redditclone.service.VoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/votes")
public class VoteController {
    private VoteService voteService;

    @PostMapping
    public void vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);

    }
}
