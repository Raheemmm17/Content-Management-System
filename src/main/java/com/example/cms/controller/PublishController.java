package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.UserDTO.PublishRequest;
import com.example.cms.UserDTO.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class PublishController {

	private PublishService service;

	public PublishController(PublishService service) {
		super();
		this.service = service;
	}

	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@RequestBody PublishRequest publishRequest,@PathVariable int postId){
		return service.publishBlogPost(publishRequest,postId);
	}

	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<PublishResponse>> UnpublishBlogPost(@PathVariable int postId){
		return service.unPublishBlogPost(postId);
	}
}
