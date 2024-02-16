package com.museumPlus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.museumPlus.dao.UserRepository;
import com.museumPlus.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int theId) {
		Optional<User> user = userRepository.findById(theId);
		if (user.isEmpty()) {
			throw new RuntimeException("Did not find the user id " + theId);
		}
		return user.get();
	}

	@Override
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		userRepository.deleteById(theId);
	}
}
