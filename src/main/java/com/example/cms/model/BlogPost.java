package com.example.cms.model;

import com.example.cms.enums.PostType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BlogPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private String title;
	private String subTitle;
	private String summary;
	private PostType postType;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	
	@ManyToOne
	@JsonIgnore
	private Blog blogs;
}
