package com.example.demo.dto.commentDtos;

public record CommentRequest(Long userId, Long tweetId, String comment) {
}
