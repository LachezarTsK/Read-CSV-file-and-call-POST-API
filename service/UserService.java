package com.museumPlus.service;

import java.util.List;

import com.museumPlus.entity.User;

public interface UserService {
	List<User> findAll();

	User findById(int theId);

	User save(User user);

	void deleteById(int theId);
}
