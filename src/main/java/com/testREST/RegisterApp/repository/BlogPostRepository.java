package com.testREST.RegisterApp.repository;


import com.testREST.RegisterApp.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}