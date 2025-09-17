package com.javatest.email_scheduler.service;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javatest.email_scheduler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;

	public void createUser(String username, String rawPassword) {
		User user = new User();
		user.setName(username);
		user.setPassword(passwordEncoder.encode(rawPassword));
		userRepo.save(user);
	}

	public boolean existsByUsername(String name) {
		return userRepo.findByName(name).isPresent();
	}

	public void createUser(String name, String rawPassword, String role) {
		User user = new User();
		user.setName(name);
		user.setPassword(passwordEncoder.encode(rawPassword));
		userRepo.save(user);
	}
}
