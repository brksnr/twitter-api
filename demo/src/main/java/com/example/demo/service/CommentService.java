package com.example.demo.service;

import com.example.demo.entity.Comments;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TweetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(UserRepository userRepository, TweetRepository tweetRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    public Comments commentToTweet(Long tweetId, Long userId, String comment){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("ilgili kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı!", HttpStatus.NOT_FOUND));

        Comments comments = new Comments();
        comments.setComment(comment);
        comments.setUser(user);
        comments.setTweets(tweet);

        commentRepository.save(comments);
        throw new ApiException("YORUM BAŞARIYLA ATILDI!", HttpStatus.OK);
    }

    public void deleteComment(Long userId, Long tweetId, Long commentId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("ilgili kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı!", HttpStatus.NOT_FOUND));


        Comments comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("ilgili yorum bulunamadı!", HttpStatus.NOT_FOUND));

        if(!user.getId().equals(comment.getUser().getId())){
            throw new ApiException("İlgili yorumu sadece yorumun sahibi silebilir!", HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

    public Comments updateComment(Long userId, Long tweetId, Long commentId, String newComment){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("ilgili kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));

        Tweets tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("ilgili tweet bulunamadı!", HttpStatus.NOT_FOUND));


        Comments comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("ilgili yorum bulunamadı!", HttpStatus.NOT_FOUND));

        if(!user.getId().equals(comment.getUser().getId())){
            throw new ApiException("İlgili yorumu sadece yorumun sahibi değiştirebilir!", HttpStatus.FORBIDDEN);
        }

        comment.setComment(newComment);

        return commentRepository.save(comment);
    }
}
