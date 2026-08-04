package com.gcg.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcg.authservice.entity.User;
import com.gcg.authservice.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}

	public void saveUser(User user) {
		repository.save(user);
	}
	
}
