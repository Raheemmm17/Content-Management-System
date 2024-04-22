package com.example.cms.serviceIMPL;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.UserDTO.BlogPostResponse;
import com.example.cms.UserDTO.PublishRequest;
import com.example.cms.UserDTO.PublishResponse;
import com.example.cms.UserDTO.ScheduleRequest;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundById;
import com.example.cms.exception.GiveTheFutureTimeException;
import com.example.cms.model.BlogPost;
import com.example.cms.model.Publish;
import com.example.cms.model.Schedule;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceIMPL implements PublishService{

	private PublishRepository publishRepository;
	private ScheduleRepository scheduleRepository;
	private BlogPostRepository postRepository;
	private ResponseStructure<PublishResponse> publishResponse;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> unPublishBlogPost(int postId) {
		return postRepository.findById(postId).map(post->{
			post.setPostType(PostType.DRAFT);
			postRepository.save(post);
			return ResponseEntity.ok(publishResponse.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Published")
					.setData(mapToPublishResponse(post.getPublish())));
		}).orElseThrow(()-> new BlogPostNotFoundById("Blog is not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {
		return postRepository.findById(postId).map(post->{
			Publish publish=null;
			if(post.getPublish()!=null) {
				publish = mapToPublishEntity(publishRequest, post.getPublish());
			}
			else {
				publish=mapToPublishEntity(publishRequest,new Publish());
			}
			if(publishRequest.getSchedule()!=null) {
				ScheduleRequest scheduleRequest = new ScheduleRequest();
				if(!scheduleRequest.getDateTime().isAfter(LocalDateTime.now()))
					throw new GiveTheFutureTimeException("Give the proper time");
				if(scheduleRequest.getDateTime()==null)
					publish.setSchedule(scheduleRepository.save(mapToScheduleEntity(publishRequest.getSchedule(),new Schedule())));
				else
//					publish.setSchedule(scheduleRepository.save(mapToScheduleEntity(publishRequest.getSchedule(),)));
				post.setPostType(PostType.SCHEDULED);
			}
			//whennevr a certain task want execute again and again , weill use scheduled jobs
			//1hr interval 
			else
				post.setPostType(PostType.PUBLISHED);
			publish.setBlogPost(post);
			publishRepository.save(publish);
			return ResponseEntity.ok(publishResponse.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Published")
					.setData(mapToPublishResponse(publish)));
		}).orElseThrow(()-> new BlogPostNotFoundById("Blog is not found"));
	}

	private Schedule mapToScheduleEntity(ScheduleRequest scheduleRequest, Schedule schedule) {
		return Schedule.builder().dateTime(scheduleRequest.getDateTime())
				.build();
	}

	private PublishResponse mapToPublishResponse(Publish post) {
		return PublishResponse.builder()
				.publishId(post.getPublishId())
				.seoTitle(post.getSeoTitle())
				.seoDescription(post.getSeoDescription())
				.seoTopics(post.getSeoTopics())
				.publishURL(post.getPublishURL())
				.blogPostResponce(mapToBlogPostResponse(post.getBlogPost()))
				.build();
	}

	private BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.postId(blogPost.getPostId())
				.title(blogPost.getTitle())
				.subTitle(blogPost.getSubTitle())
				.summary(blogPost.getSummary())
				.blogs(blogPost.getBlogs())
				.build();
	}

	private Publish mapToPublishEntity(PublishRequest publishRequest, Publish publish) {
		return Publish.builder()
				.seoTitle(publishRequest.getSeoTitle())
				.seoDescription(publishRequest.getSeoDescription())
				.seoTopics(publishRequest.getSeoTopics())
				.build();
	}
}
