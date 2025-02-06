package com.example.demo.service;

import com.example.demo.entity.ReTweets;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.ReTweetRepository;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReTweetService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ReTweetRepository reTweetRepository;

    @Autowired
    public ReTweetService(UserRepository userRepository, TweetRepository tweetRepository, ReTweetRepository reTweetRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.reTweetRepository = reTweetRepository;
    }

    public ReTweets reTweet(Long tweetId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));

        Optional<ReTweets> reTweet = reTweetRepository.findByUserAndTweets(user,tweet);

        if(reTweet.isPresent()){
            throw new ApiException("Bu tweeti daha önce retweetlediniz.", HttpStatus.BAD_REQUEST);
        }

        ReTweets reTweets = new ReTweets();
        reTweets.setTweets(tweet);
        reTweets.setUser(user);

        return reTweetRepository.save(reTweets);
    }

    public void deleteReTweet(Long tweetId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));


        ReTweets reTweet = reTweetRepository.findByUserAndTweets(user,tweet)
                .orElseThrow(() -> new ApiException("Retweeti sadece retweet sahibi geri alabilir!", HttpStatus.FORBIDDEN));

        reTweetRepository.delete(reTweet);
    }
}
