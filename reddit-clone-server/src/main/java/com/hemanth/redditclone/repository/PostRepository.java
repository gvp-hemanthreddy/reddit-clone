package com.hemanth.redditclone.repository;

import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.Subreddit;
import com.hemanth.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
