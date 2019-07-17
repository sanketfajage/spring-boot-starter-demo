package com.practice.springbootstarter.repository;

import com.practice.springbootstarter.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
