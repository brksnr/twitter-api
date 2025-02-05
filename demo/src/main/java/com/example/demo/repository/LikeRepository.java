package com.example.demo.repository;

import com.example.demo.entity.Likes;
import com.example.demo.entity.Tweets;
import com.example.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndTweets(User user, Tweets tweet);
}
