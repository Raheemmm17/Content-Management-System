package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.UserDTO.UserRequestDTO;
import com.example.cms.UserDTO.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {
	
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequestDTO user);
	
}
