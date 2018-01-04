package com.packtpub.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.packtpub.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Override
	public List<User> getAllUsers() {
		return this.users;
	}

	// Dummy users
	public static List<User> users;

	public UserServiceImpl() {
		users = new LinkedList<>();
		users.add(new User(100, "David"));
		users.add(new User(101, "Peter"));
		users.add(new User(102, "John"));
	}
}