package com.example.demo.repository;

import com.example.demo.entity.ReTweets;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReTweetRepository extends JpaRepository<ReTweets, Long> {

    Optional<ReTweets> findByUserAndTweets(User user, Tweets tweet);
}
