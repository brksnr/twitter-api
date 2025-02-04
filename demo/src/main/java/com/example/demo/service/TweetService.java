package com.example.demo.service;

import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Tweets createTweet(String content, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = new Tweets();
        tweet.setContent(content);
        tweet.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }
}
