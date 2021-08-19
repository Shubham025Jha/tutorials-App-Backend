package com.tutorials.practice.service;

import com.tutorials.practice.domain.User;

public interface UserService {
     public User saveUser(User user );
     public User fetchUserByEmailId(String email);
     public User checkLoginInfo(String emailString,String passwordString);
}
