package com.example.demo.entity;


import com.example.demo.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "likes", schema = "twitter")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweets tweets;


}
