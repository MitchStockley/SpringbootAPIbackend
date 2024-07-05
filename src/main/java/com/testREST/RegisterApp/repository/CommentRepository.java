package com.testREST.RegisterApp.repository;

import com.testREST.RegisterApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogPostId(Long postId);
}
