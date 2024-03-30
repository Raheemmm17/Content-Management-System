package com.example.cms.serviceIMPL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.UserDTO.BlogRequest;
import com.example.cms.UserDTO.BlogResponce;
import com.example.cms.exception.BlogNotFoundByBlogIdException;
import com.example.cms.exception.TitleNotAvailableException;
import com.example.cms.exception.TopicNotSpecifiedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.Panelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BlogServiceIMPL implements BlogService{

	private UserRepository userRepository;
	private BlogRepository blogRepository;
	private Panelrepository panelRepository;
	private ResponseStructure<BlogResponce> structure;
	private ResponseStructure<Boolean> bstructure;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponce>> createBlog(@Valid BlogRequest blogRequest, int userId) {
		return userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new TitleNotAvailableException("Title is already present");
			if(blogRequest.getTopics().length<1)
				throw new TopicNotSpecifiedException("Failed to create blog");
			Blog blog = mapToBlogEntity(blogRequest,new Blog());

			ContributionPanel contributionPanel =panelRepository.save(new ContributionPanel());
			blog.setContributionPanel(contributionPanel);
			blog.setUser(user);
			blog = blogRepository.save(blog);
			
			userRepository.save(user);
			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Saved Successfully")
					.setData(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failed to insert"));		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponce>> updateBlogByid(BlogRequest blogRequest, int blogId) {
		if(blogRepository.existsByTitle(blogRequest.getTitle()))
			throw new TitleNotAvailableException("Title is already present");
		if(blogRequest.getTopics().length<1)
			throw new TopicNotSpecifiedException("Failed to create blog");

		return blogRepository.findById(blogId).map(exBlog->{
			Blog blog = blogRepository.save(mapToBlogEntity(blogRequest,exBlog));
			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog Saved Successfully")
					.setData(mapToBlogResponse(blog)));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failed to insert"));
	}

	@Override
	public ResponseEntity<ResponseStructure<Boolean>> checkBlogTitleAvailaiblity(String title) {
		return ResponseEntity.ok(bstructure.setStatusCode(HttpStatus.OK.value())
				.setMessage(blogRepository.existsByTitle(title)?"Blog title already in used":"Blog title available")
				.setData(blogRepository.existsByTitle(title)));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogResponce>> findBlogById(int blogId) {
		return blogRepository.findById(blogId).map(blog->{
			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.FOUND.value())
					.setMessage("Blog is found successfully")
					.setData(mapToBlogResponse(blog)));})
				.orElseThrow(()->new BlogNotFoundByBlogIdException("Blog is not present with given Blog Id"));
	}

	private Blog mapToBlogEntity(BlogRequest blogRequest,Blog exBlog) {
		exBlog.setTitle(blogRequest.getTitle());
		exBlog.setAbout(blogRequest.getAbout());
		exBlog.setTopics(blogRequest.getTopics());
		return exBlog;
	}
	private BlogResponce mapToBlogResponse(Blog blog) {
		return BlogResponce.builder()
				.blogId(blog.getBlogId())
				.title(blog.getTitle())
				.topics(blog.getTopics())
				.about(blog.getAbout())
				.user(blog.getUser())
				.build();
	}
}