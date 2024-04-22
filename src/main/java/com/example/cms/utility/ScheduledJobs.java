package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduledJobs {
	private BlogPostRepository blogPostRepository;
	@Scheduled(fixedDelay = 60*1000l)
	public void publishScheduledBlogPosts() {
		List<BlogPost> blogPost =blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(), PostType.SCHEDULED)
				.stream().map(post->{
					post.setPostType(PostType.PUBLISHED);
					return post;
				}).collect(Collectors.toList());
		blogPostRepository.saveAll(blogPost);
	}
}
