package com.packtpub.reactive;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserHandler {

	private final UserRepository userRepository;
	
	public UserHandler(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	public Mono<ServerResponse> getAllUsers(ServerRequest request){
		Flux<User> users = this.userRepository.getAllUsers();
		return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User.class);		
	}
	
	public Mono<ServerResponse> getUser(ServerRequest request){
		
		int userId = Integer.valueOf(request.pathVariable("id"));
		
		System.out.println("trap "+userId);
		
		Mono<ServerResponse> notFound = ServerResponse.notFound().build();
		Mono<User> userMono = this.userRepository.getUser(userId);
		
		System.out.println("trap1 "+userMono);
		
		return userMono
				.flatMap(user -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(user)))
				.switchIfEmpty(notFound);		
	}	
	
	public Mono<ServerResponse> createUser(ServerRequest request) {
		System.out.println("test1");
		Mono<User> user = request.bodyToMono(User.class);
		return ServerResponse.ok().build(this.userRepository.saveUser(user));
	}
}
