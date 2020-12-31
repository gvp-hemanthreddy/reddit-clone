package com.hemanth.redditclone.repository;

import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import com.hemanth.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
