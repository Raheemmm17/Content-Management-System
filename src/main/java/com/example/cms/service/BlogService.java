package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.UserDTO.BlogRequest;
import com.example.cms.UserDTO.BlogResponce;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponce>> createBlog(@Valid BlogRequest blog, int userId);

	ResponseEntity<ResponseStructure<BlogResponce>> updateBlogByid(BlogRequest blog, int blogId);

	ResponseEntity<ResponseStructure<Boolean>> checkBlogTitleAvailaiblity(String title);

	ResponseEntity<ResponseStructure<BlogResponce>> findBlogById(int blogId);

}
