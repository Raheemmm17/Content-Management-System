package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.BlogPostRequest;
import com.example.cms.UserDTO.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class BlogPostController {

	private BlogPostService service;
	public BlogPostController(BlogPostService service) {
		super();
		this.service = service;
	}

	@Operation(description = "this endponit is used to add the blog post", responses = {
			@ApiResponse(responseCode = "200", description = "Blogpost is added Sucessfully"),
			@ApiResponse(responseCode = "404", description = "Blog is not added") })
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> addBlogPost(@PathVariable @Valid int blogId, @RequestBody BlogPostRequest blogPostRequest){
		return service.createDraft(blogId, blogPostRequest);
	}

	@Operation(description = "this endponit is used to update the blog post", responses = {
			@ApiResponse(responseCode = "200", description = "Blogpost is updated Sucessfully "),
			@ApiResponse(responseCode = "404", description = "Blog is not updated") })
	@PutMapping("/blogposts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(@PathVariable int blogId, @RequestBody BlogPostRequest blogPostRequest){
		return service.updateDraft(blogId, blogPostRequest);
	}
}
