package com.example.cms.UserDTO;

import com.example.cms.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogResponce {
	
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	 private User user;
}
