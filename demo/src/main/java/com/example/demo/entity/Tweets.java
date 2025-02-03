package com.example.demo.entity;


import com.example.demo.entity.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

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



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;


    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReTweets> retweets;


    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;
}
