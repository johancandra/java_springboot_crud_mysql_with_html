package com.spring.java_springboot_crud_mysql.repository;

import com.spring.java_springboot_crud_mysql.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
