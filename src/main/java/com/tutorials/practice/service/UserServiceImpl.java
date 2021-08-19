package com.tutorials.practice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorials.practice.dal.UserRepository;
import com.tutorials.practice.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User saveUser(User user) {
		user.setAdmin(false);
		String emailString=user.getEmailId();
		
		if(emailString==null || "".equals(emailString)) return null;
		
		Optional<User> uOptional=Optional.ofNullable(fetchUserByEmailId(emailString));
		
		
		 if(uOptional.isEmpty())
		    return userRepo.save(new User(user.getEmailId(),user.getUserName(),user.getPassword(),user.isAdmin()));
		 else {
			return null;
		 }
	}

	@Override
	public User fetchUserByEmailId(String email) {
		return userRepo.findByEmailId(email);
		 
	}

	@Override
	public User checkLoginInfo(String emailString, String passwordString) {
		Optional<User> uOptional=Optional.ofNullable(fetchUserByEmailId(emailString));
		
		 if(uOptional.isEmpty())
			    return null;
			 else {
				boolean b=uOptional.get().getPassword().equals(passwordString);
				if(b) {
					return uOptional.get();
				}
				else {
					return null;
				}
			 }
		
		
	}

}
