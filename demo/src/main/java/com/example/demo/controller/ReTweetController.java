package com.example.demo.controller;

import com.example.demo.entity.ReTweets;
import com.example.demo.entity.Tweets;
import com.example.demo.service.ReTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class ReTweetController {

    private final ReTweetService reTweetService;

    @Autowired
    public ReTweetController(ReTweetService reTweetService) {
        this.reTweetService = reTweetService;
    }

    @PostMapping("/tweet/{tweetId}/user/{userId}")
    public ResponseEntity<ReTweets  > reTweet(@PathVariable Long tweetId,@PathVariable Long userId){
        ReTweets reTweet = reTweetService.reTweet(tweetId, userId);
        return ResponseEntity.ok(reTweet);
    }

    @DeleteMapping("/deleteTweet/{tweetId}/user/{userId}")
    public ResponseEntity<String> deleteReTweet(@PathVariable Long tweetId,@PathVariable Long userId){
        reTweetService.deleteReTweet(tweetId, userId);
        return ResponseEntity.ok("retweet başarıyla kaldırıldı!");
    }
}