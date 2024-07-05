package com.testREST.RegisterApp.controller;

import com.testREST.RegisterApp.exception.ResourceNotFoundException;
import com.testREST.RegisterApp.model.BlogPost;
import com.testREST.RegisterApp.model.Comment;
import com.testREST.RegisterApp.repository.BlogPostRepository;
import com.testREST.RegisterApp.service.BlogPostService;
import com.testREST.RegisterApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private CommentService commentService;



    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogPostService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        BlogPost post = blogPostService.getPostById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @PostMapping(consumes = "multipart/form-data")
    public BlogPost createPost(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("username") String username,
                               @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            BlogPost post = new BlogPost();
            post.setTitle(title);
            post.setContent(content);
            post.setUsername(username);
            if (file != null) {
                post.setImage(file.getBytes());
            }
            return blogPostService.createPost(post);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing file upload", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPost postDetails) {
        BlogPost updatedPost = blogPostService.updatePost(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/comments")
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        BlogPost post = blogPostService.getPostById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        comment.setBlogPost(post);
        return commentService.createComment(comment);
    }

    @GetMapping("/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }


}
 