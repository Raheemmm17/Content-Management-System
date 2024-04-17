package com.example.cms.UserDTO;

//import java.time.LocalDateTime;

import com.example.cms.enums.PostType;
import com.example.cms.model.Blog;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class BlogPostResponse {
	private int postId;
	private String title;
	private String subTitle;
	private String summary;
	private PostType postType;
	//	private LocalDateTime lastModifiedAt;
	//	private String createdBy;
	//	private String lastModifiedBy;
	//	private  LocalDateTime createdAt;

	private Blog blogs;
}
