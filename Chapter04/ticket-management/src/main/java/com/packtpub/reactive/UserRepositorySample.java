package com.packtpub.reactive;

import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Flux;

public class UserRepositorySample implements UserRepository {

	// initiate Users
	private final Map<Integer, User> users = new HashMap<>();

	// fill dummy values for testing
	public UserRepositorySample() {
		this.users.put(1, new User(100, "David"));
		this.users.put(2, new User(101, "John"));
		this.users.put(3, new User(102, "Kevin"));
	}

	// this method will return all users
	@Override
	public Flux<User> getAllUsers() {
		return Flux.fromIterable(this.users.values());
	}
}