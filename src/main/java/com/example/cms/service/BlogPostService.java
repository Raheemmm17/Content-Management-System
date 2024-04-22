package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.UserDTO.BlogPostRequest;
import com.example.cms.UserDTO.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId, BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int blogId, BlogPostRequest blogPostRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> deleteDraft(int postId);

}
