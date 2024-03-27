package com.example.cms.serviceIMPL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.UserDTO.UserRequestDTO;
import com.example.cms.UserDTO.UserResponse;
import com.example.cms.exception.EmailAlreadyPresentException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceIMPL implements UserService{

	private UserRepository repository;
	private ResponseStructure<UserResponse> structure;
	private PasswordEncoder password;
	

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequestDTO userRequest) {
		if(repository.existsByEmail(userRequest.getEmail()))
			throw new EmailAlreadyPresentException("Failed to register");
		
		User user = repository.save(mapToUser(userRequest, new User()));
		
		return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
				             				.setMessage("User registered successfully")
				             				.setData(mapToUserResponse(user)));
	}
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();
	}
	private User mapToUser(UserRequestDTO userRequest, User user) {
		user.setUsername(userRequest.getUsername());
		user.setEmail(userRequest.getEmail());
		user.setPassword(password.encode(userRequest.getPassword()));
		return user;
	}
}
