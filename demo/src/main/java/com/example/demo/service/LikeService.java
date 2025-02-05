package com.example.demo.service;

import com.example.demo.entity.Likes;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(UserRepository userRepository, TweetRepository tweetRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.likeRepository = likeRepository;
    }

    public Likes likeTweet(Long tweetId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("İlgili kullanıcı bulunamadı.", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı.", HttpStatus.NOT_FOUND));

        Optional<Likes> existingLike = likeRepository.findByUserAndTweets(user, tweet);
        if (existingLike.isPresent()) {
            throw new ApiException("Bu tweeti daha önce beğendiniz.", HttpStatus.BAD_REQUEST);
        }

        Likes likes = new Likes();
        likes.setUser(user);
        likes.setTweets(tweet);

        return likeRepository.save(likes);
    }

    public void deleteLike(Long tweetId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("İlgili kullanıcı bulunamadı.", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı.", HttpStatus.NOT_FOUND));

        Likes existingLike = likeRepository.findByUserAndTweets(user, tweet)
                .orElseThrow(() -> new ApiException("Bu tweeti beğenmediğiniz için beğeniyi geri alamazsınız!", HttpStatus.BAD_REQUEST));

        likeRepository.delete(existingLike);
    }
}
