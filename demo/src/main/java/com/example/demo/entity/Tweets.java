package com.example.demo.entity;


import com.example.demo.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tweets", schema = "twitter")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @NotNull
    @Size(min = 20, max = 280, message = "Tweet 20 ile 280 karakter arasında olmalıdır.")
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    @JsonManagedReference
    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReTweets> retweets;

    @JsonManagedReference
    @OneToMany(mappedBy = "tweets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public List<ReTweets> getRetweets() {
        return retweets;
    }

    public void setRetweets(List<ReTweets> retweets) {
        this.retweets = retweets;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
