package com.hemanth.redditclone.repository;

import com.hemanth.redditclone.model.Comment;
import com.hemanth.redditclone.model.Post;
import com.hemanth.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtDesc(Post post);

    List<Comment> findByUser(User user);

    Optional<Comment> findByIdentifier(String identifier);
}
