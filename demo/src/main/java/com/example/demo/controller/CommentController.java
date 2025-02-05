package com.example.demo.controller;

import com.example.demo.dto.CommentRequest;
import com.example.demo.entity.Comments;
import com.example.demo.service.CommentService;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final UserService userService;
    private final TweetService tweetService;
    private final CommentService commentService;

    @Autowired
    public CommentController(UserService userService, TweetService tweetService, CommentService commentService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.commentService = commentService;
    }

    @PostMapping("/user/{userId}/tweet/{tweetId}")
    public ResponseEntity<Comments> commentToTweet(@PathVariable Long userId, @PathVariable Long tweetId,@RequestBody CommentRequest request){
        Comments comment = commentService.commentToTweet(tweetId,userId, request.comment());

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/user/{userId}/tweet/{tweetId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long userId,@PathVariable Long tweetId,@PathVariable Long commentId){
         commentService.deleteComment(userId,tweetId , commentId);
           return  ResponseEntity.ok("Yorum başarıyla silindi!");
    }


}
