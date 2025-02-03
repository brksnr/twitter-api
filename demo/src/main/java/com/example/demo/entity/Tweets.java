package com.example.demo.entity;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tweets", schema = "twitter")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String Content;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
