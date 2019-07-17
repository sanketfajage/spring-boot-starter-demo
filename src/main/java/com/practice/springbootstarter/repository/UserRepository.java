package com.practice.springbootstarter.repository;

import com.practice.springbootstarter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
