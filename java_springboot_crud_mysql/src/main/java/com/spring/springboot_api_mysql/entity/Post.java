package com.spring.java_springboot_crud_mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String authorname;
    @Column(nullable = false)
    private Long viewers;
    @Column(nullable = false)
    private String datecreated;
    //@Column(nullable = false, unique = true)
    //private String email;
}