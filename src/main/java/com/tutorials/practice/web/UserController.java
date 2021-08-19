package com.tutorials.practice.web;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.practice.domain.User;
import com.tutorials.practice.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
	private UserService userService;
	
	@PostMapping("/registeruser")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		Optional<User> uOptional=Optional.ofNullable(userService.saveUser(user));
		
		if(uOptional.isPresent()) {
			
			return new ResponseEntity<>(uOptional.get(),HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		Optional<String> emailString=Optional.ofNullable(user.getEmailId());
		Optional<String> passwordOptional=Optional.ofNullable(user.getPassword());
		
		if(emailString.isEmpty() || passwordOptional.isEmpty()) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		else {
			Optional<User> uOptional=Optional.ofNullable(userService.checkLoginInfo(emailString.get(), passwordOptional.get()));
			if(uOptional.isPresent()){
				return new ResponseEntity<User>(uOptional.get(),HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			}
		}
		
	}
	
	 
	
}
