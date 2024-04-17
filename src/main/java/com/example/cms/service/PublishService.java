package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.UserDTO.PublishRequest;
import com.example.cms.UserDTO.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest, int postId);

	ResponseEntity<ResponseStructure<PublishResponse>> unPublishBlogPost(int postId);
}
