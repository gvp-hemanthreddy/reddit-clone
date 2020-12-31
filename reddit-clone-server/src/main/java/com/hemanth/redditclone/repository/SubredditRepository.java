package com.hemanth.redditclone.repository;

import com.hemanth.redditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
//    @Query("FROM subreddits WHERE name = ?1")
    boolean existsByName(String name);
    Optional<Subreddit> findByName(String name);
}
