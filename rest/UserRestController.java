package com.museumPlus.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.museumPlus.entity.User;
import com.museumPlus.service.UserService;

@RestController
@RequestMapping("/reqres.in")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public List<User> findAll() {
		return userService.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable int id) {

		User user = userService.findById(id);

		if (user == null) {
			throw new RuntimeException("User not found: " + id);
		}

		return user;
	}

	@PostMapping("/users")
	public User addEmployee(@RequestBody User user) {
		User dbEmployee = userService.save(user);
		return dbEmployee;
	}

	@PutMapping("/users")
	public User updateEmployee(@RequestBody User user) {
		User dbUser = userService.save(user);
		return dbUser;
	}

	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable int id) {
		User user = userService.findById(id);

		if (user == null) {
			throw new RuntimeException("User not found: " + id);
		}

		userService.deleteById(id);

		return "Deleted user id: " + id;
	}
}
