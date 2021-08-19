package com.tutorials.practice.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorials.practice.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	 public User findByEmailId(String email);
}
