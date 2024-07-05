package com.testREST.RegisterApp.service;

import com.testREST.RegisterApp.exception.ResourceNotFoundException;

import com.testREST.RegisterApp.model.BlogPost;
import com.testREST.RegisterApp.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;


    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    public BlogPost createPost(BlogPost post) {
        return blogPostRepository.save(post);
    }

    public BlogPost updatePost(Long id, BlogPost postDetails) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setUsername(postDetails.getUsername());
        if (postDetails.getImage() != null) {
            post.setImage(postDetails.getImage());
        }
        return blogPostRepository.save(post);
    }

    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }


}
