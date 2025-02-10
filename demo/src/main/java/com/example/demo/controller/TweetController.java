package com.example.demo.controller;

import com.example.demo.dto.tweetDtos.PostTweetRequest;
import com.example.demo.dto.tweetDtos.UpdateTweet;
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

    @GetMapping
    public ResponseEntity<List<Tweets>> getAllTweets() {
        List<Tweets> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/{userId}")
    public List<Tweets> getAllTweetsById(@PathVariable Long userId) {
        return tweetService.getAllTweetsById(userId);
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<Tweets> getTweetWithInfo(@PathVariable Long tweetId) {
        Tweets tweet = tweetService.getTweetWithInfo(tweetId);
        return ResponseEntity.ok(tweet);
    }

    @DeleteMapping("/user/{userId}/tweet/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        tweetService.deleteTweetById(userId, tweetId);
        return ResponseEntity.ok("Tweet başarıyla silindi!");
    }

    @PutMapping("/user/{userId}/upadteTweet/{tweetId}")
    public ResponseEntity<Tweets> updateTweet(@PathVariable  Long userId, @PathVariable Long tweetId, @RequestBody UpdateTweet newContent){

        Tweets updatedTweet = tweetService.updateTweet(userId, tweetId, newContent.content());
        return ResponseEntity.ok(updatedTweet);
    }


}
