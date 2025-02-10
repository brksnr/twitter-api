package com.example.demo.dto.tweetDtos;

public class TweetUserDto {
    private String email;
    private String username;

    public TweetUserDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    // Getter'lar ve Setter'lar
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}

