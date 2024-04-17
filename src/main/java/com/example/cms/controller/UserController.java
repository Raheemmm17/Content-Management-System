package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.UserRequestDTO;
import com.example.cms.UserDTO.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class UserController {

	private UserService service;

	public UserController(UserService service) {
		super();
		this.service = service;
	}

	@Operation(description = "This end point is for add the user", responses = {
			@ApiResponse(responseCode = "200",description = "User saved successfully"),
			@ApiResponse(responseCode = "404",description = "User not saved")})
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(@RequestBody @Valid UserRequestDTO user) {
		return service.registerUser(user);
	}
	@Operation(description = "This end point is for delete the user", responses = {
			@ApiResponse(responseCode = "200",description = "User Deleted successfully"),
			@ApiResponse(responseCode = "404",description = "User not Deleted")})
	@DeleteMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId){
		return service.deleteUser(userId);
	}
	@Operation(description = "This end point is for add the user", responses = {
			@ApiResponse(responseCode = "200",description = "User Found successfully"),
			@ApiResponse(responseCode = "404",description = "User not Found")})
	@GetMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(@PathVariable int userId){
		return service.findUniqueUser(userId);
	}

	@GetMapping("/test")
	public String test() {
		return "Virat from cms";
	}
}
