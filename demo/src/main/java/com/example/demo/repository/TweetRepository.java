package com.example.demo.repository;

import com.example.demo.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweets, Long> {
}
