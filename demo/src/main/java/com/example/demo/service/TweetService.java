package com.example.demo.service;

import com.example.demo.dto.tweetDtos.TweetDto;
import com.example.demo.dto.tweetDtos.TweetUserDto;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TweetDto> getAllTweets() {
        List<Tweets> tweets = tweetRepository.findAll();
        List<TweetDto> tweetDtos = new ArrayList<>();

        for (Tweets tweet : tweets) {
            TweetUserDto userDto = new TweetUserDto(tweet.getUser().getEmail(), tweet.getUser().getUsername());

            List<String> likeEmails = tweet.getLikes().stream()
                    .map(like -> like.getUser().getEmail())
                    .collect(Collectors.toList());

            List<String> retweetEmails = tweet.getRetweets().stream()
                    .map(retweet -> retweet.getUser().getEmail())
                    .collect(Collectors.toList());

            List<String> commentContents = tweet.getComments().stream()
                    .map(comment -> comment.getComment())
                    .collect(Collectors.toList());

            TweetDto tweetDto = new TweetDto(
                    tweet.getId(),
                    tweet.getContent(),
                    userDto,
                    tweet.getCreatedAt(),
                    likeEmails,
                    retweetEmails,
                    commentContents
            );

            tweetDtos.add(tweetDto);
        }

        return tweetDtos;
    }


    public List<Tweets> getAllTweetsById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        return user.getTweets();
    }


    public Tweets getTweetWithInfo(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("İlgili tweet bulunamadı.", HttpStatus.NOT_FOUND));
    }

    public void deleteTweetById(Long userId, Long tweetId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("İlgili kullanıcı bulunamadı.", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı.", HttpStatus.NOT_FOUND));

        if(!user.getId().equals(tweet.getUser().getId())){
            throw new ApiException("Bu tweeti silme yetkiniz yok!", HttpStatus.FORBIDDEN);
        }
        tweetRepository.delete(tweet);
    }

    public Tweets updateTweet(Long userId, Long tweetId, String newContent){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("İlgili kullanıcı bulunamadı.", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı.", HttpStatus.NOT_FOUND));

        if(!user.getId().equals(tweet.getUser().getId())){
            throw new ApiException("Bu tweeti değiştirme yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        tweet.setContent(newContent);

        return tweetRepository.save(tweet);
    }

}
