package com.example.demo.controller;

import com.example.demo.dto.PostTweetRequest;
import com.example.demo.entity.Tweets;
import com.example.demo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/create")
    public ResponseEntity<Tweets> createTweet(@RequestBody PostTweetRequest request){
        Tweets createdTweet = tweetService.createTweet(request.content(), request.userId());
        return ResponseEntity.ok(createdTweet);
    }

    @GetMapping("/{userId}")
    public List<Tweets> getAllTweetsById(@PathVariable Long userId) {
        return tweetService.getAllTweetsById(userId);
    }

}
