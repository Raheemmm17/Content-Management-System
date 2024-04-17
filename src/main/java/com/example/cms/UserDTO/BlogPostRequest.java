package com.example.cms.UserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogPostRequest {
	@NotNull(message = "Title cannot be null")
	private String title;
	private String subTitle;
	//	@Size(min=250, message = "Summary should be at least 500 characters long")
	private String summary;
}
