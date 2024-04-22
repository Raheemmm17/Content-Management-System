package com.example.cms.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	private String publishURL;

	@OneToOne
	private BlogPost blogPost;

	@OneToOne
	private Schedule schedule;
}
