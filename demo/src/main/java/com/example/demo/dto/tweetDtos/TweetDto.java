package com.example.demo.dto.tweetDtos;

import java.sql.Timestamp;
import java.util.List;

public class TweetDto {
    private Long id;
    private String content;
    private TweetUserDto user;
    private Timestamp timestamp;
    private List<String> likes;
    private List<String> retweets;
    private List<String> comments;

    public TweetDto(Long id, String content, TweetUserDto user, Timestamp timestamp, List<String> likes, List<String> retweets, List<String> comments) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public TweetUserDto getUser() {
        return user;
    }

    public List<String> getLikes() {
        return likes;
    }

    public List<String> getRetweets() {
        return retweets;
    }

    public List<String> getComments() {
        return comments;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
