package com.spring.java_springboot_crud_mysql.controller;

import lombok.AllArgsConstructor;
import com.spring.java_springboot_crud_mysql.entity.Post;
import com.spring.java_springboot_crud_mysql.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post savedPost = postService.createPost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    // build get post by id REST API
    // http://localhost:8080/api/posts/create
    @GetMapping("create")
    public ResponseEntity<String> create(){
        String post = postService.create();
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // build get post by id REST API
    // http://localhost:8080/api/posts/modify/1
    @GetMapping("modify/{id}")
    public ResponseEntity<String> modify(@PathVariable("id") Long postId){
        String post = postService.modify(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // build get post by id REST API
    // http://localhost:8080/api/posts/1
    @GetMapping("{id}")
    public ResponseEntity<String> getPostById(@PathVariable("id") Long postId){
        String post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Build Get All posts REST API
    // http://localhost:8080/api/posts
    @GetMapping
    public ResponseEntity<String> getAllPosts(){
        String posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Build Update post REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/posts/1
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long postId,
                                           @RequestBody Post post){
        post.setId(postId);
        Post updatedPost= postService.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Build Delete post REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("post successfully deleted!", HttpStatus.OK);
    }
}
