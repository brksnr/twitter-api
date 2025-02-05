package com.example.demo.controller;

import com.example.demo.entity.Likes;
import com.example.demo.repository.LikeRepository;
import com.example.demo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/tweet/{tweetId}/user/{userId}")
    public ResponseEntity<Likes> likeTweet(@PathVariable Long tweetId,@PathVariable Long userId){
        Likes like = likeService.likeTweet(tweetId,userId);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping("/tweet/{tweetId}/user/{userId}")
    public ResponseEntity<String> deleteTweet(@PathVariable Long tweetId,@PathVariable Long userId){
         likeService.deleteLike(tweetId, userId);
         return ResponseEntity.ok("Like Başarıyla silindi!");
    }
}
