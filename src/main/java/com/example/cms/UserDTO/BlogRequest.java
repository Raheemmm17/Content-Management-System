package com.example.cms.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogRequest {
	@NotBlank(message = "Blog Name is required")
	@NotNull(message = "Blog Name is required")
	private String title;
	
	private String[] topics;
	private String about;
}
