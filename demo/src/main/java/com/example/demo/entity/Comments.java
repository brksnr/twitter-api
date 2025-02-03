package com.example.demo.entity;


import com.example.demo.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "comments", schema = "twitter")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweets tweets;

}
