package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.BlogRequest;
import com.example.cms.UserDTO.BlogResponce;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class BlogController {

	BlogService service;

	public BlogController(BlogService service) {
		super();
		this.service = service;
	}
	@Operation(description = "This end point is for add the product", responses = {
			@ApiResponse(responseCode = "200",description = "Blog saved successfully"),
			@ApiResponse(responseCode = "404",description = "Blog not saved")})
	@PostMapping("/users/{userId}/blogs")
	private ResponseEntity<ResponseStructure<BlogResponce>> createBlog(@RequestBody @Valid BlogRequest blog,@PathVariable int userId){
		return service.createBlog(blog,userId);
	}

	@Operation(description = "this endpoit is update the blog using ID ", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is updation is completed "),
			@ApiResponse(responseCode = "404", description = "Blog updation is not updated") })
	@PutMapping("/blogs/{blogId}")
	private ResponseEntity<ResponseStructure<BlogResponce>> updateBlogByid(@RequestBody @Valid BlogRequest blog, @PathVariable int blogId){
		return service.updateBlogByid(blog,blogId);
	}

	@Operation(description = "this endpoit  is used to check Title is Available or not ", responses = {
			@ApiResponse(responseCode = "200", description = "Title is Available You can Creat "),
			@ApiResponse(responseCode = "404", description = "Title is Already Taken") })
	@GetMapping("/titles/{title}/blogs")
	public  ResponseEntity<ResponseStructure<Boolean>> checkForBlogTitleAvailability( @PathVariable String title) {
		return service.checkBlogTitleAvailaiblity(title);
	}

	@Operation(description = "this endpoit  is used to check Blog is Present or not Based On BlogId ", responses = {
			@ApiResponse(responseCode = "200", description = "Blog is Found Sucessfully "),
			@ApiResponse(responseCode = "404", description = "Blog Id Not Found") })
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponce>> findBlogById(@PathVariable int blogId){
		return service.findBlogById(blogId);
	}
}
