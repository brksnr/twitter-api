package com.example.demo.repository;

import com.example.demo.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments ,Long> {
}
