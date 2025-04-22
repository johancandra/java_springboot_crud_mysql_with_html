package com.spring.java_springboot_crud_mysql.service;

import com.spring.java_springboot_crud_mysql.entity.Post;

import java.util.List;

public interface PostService {
    String create();
    
    String modify(Long postId);

    Post createPost(Post post);

    String getPostById(Long postId);

    String getAllPosts();

    Post updatePost(Post post);

    void deletePost(Long postId);
}