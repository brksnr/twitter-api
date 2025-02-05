package com.example.demo.dto;

public record CommentRequest(Long userId, Long tweetId, String comment) {
}
