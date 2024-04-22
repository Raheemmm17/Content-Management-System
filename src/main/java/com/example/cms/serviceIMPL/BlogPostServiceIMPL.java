package com.example.cms.serviceIMPL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.UserDTO.BlogPostRequest;
import com.example.cms.UserDTO.BlogPostResponse;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.NotAValidUserException;
import com.example.cms.model.Blog;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.Panelrepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

@Service
public class BlogPostServiceIMPL implements BlogPostService{

	private UserRepository userRepository;
	private Panelrepository panelrepository;
	private BlogRepository blogRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> postStructure;
	public BlogPostServiceIMPL(UserRepository userRepository, BlogRepository blogRepository,
			BlogPostRepository blogPostRepository, ResponseStructure<BlogPostResponse> postStructure,
			Panelrepository panelrepository) {
		super();
		this.userRepository = userRepository;
		this.blogRepository = blogRepository;
		this.blogPostRepository = blogPostRepository;
		this.postStructure = postStructure;
		this.panelrepository = panelrepository;
	}

	private boolean validateUser(String email, Blog blog) {
		return blog.getUser().getEmail().equals(email)|| panelrepository.existsByPanelIdAndContributors(blog.getContributionPanel().getPanelId(),blog.getUser());
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId, BlogPostRequest blogPostRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(user->{
			return blogRepository.findById(blogId).map(blog->{
				if(!validateUser(email,blog))
					throw new NotAValidUserException("User Authentication failed");

				BlogPost blogPost = mapToBlogPost(blogPostRequest);
				blogPost.setBlogs(blog);
				blogPostRepository.save(blogPost);
				return ResponseEntity.ok(postStructure.setStatusCode(HttpStatus.OK.value())
						.setMessage("Blog Post Created")
						.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()-> new BlogNotFoundByIdException("Blog not found by given id"));
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,
			BlogPostRequest blogPostRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			Blog blog = blogPost.getBlogs();
			if(!validateUser(email,blog))
				throw new NotAValidUserException("User Authentication failed");
			BlogPost updatedBlogPost = mapToBlogPost(blogPostRequest);
			updatedBlogPost.setPostId(postId);
			updatedBlogPost=blogPostRepository.save(updatedBlogPost);
			return ResponseEntity.ok(postStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Blog post is created")
					.setData(mapToBlogPostResponse(updatedBlogPost)));
		}).orElseThrow(()-> new BlogNotFoundByIdException  ("Blog is not present with the given id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteDraft(int postId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(post->{
			if(!validateUser(email,post.getBlogs()))
				throw new NotAValidUserException("user authentication failed");
			blogPostRepository.delete(post);
			return ResponseEntity.ok(postStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Draft deleted successfully")
					.setData(mapToBlogPostResponse(post)));
		}).orElseThrow(()-> new BlogNotFoundByIdException("Draft not found by given ID"));
	}

	BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.postId(blogPost.getPostId())
				.title(blogPost.getTitle())
				.subTitle(blogPost.getSubTitle())
				.summary(blogPost.getSummary())
				.postType(blogPost.getPostType())
				.blogs(blogPost.getBlogs())
//				.createdBy(blogPost.getCreatedBy())
//				.lastModifiedBy(blogPost.getLastModifiedBy())
//				.createdAt(blogPost.getCreatedAt())
//				.lastModifiedAt(blogPost.getLastModifiedAt())
				.build();
	}
	BlogPost mapToBlogPost(BlogPostRequest blogPostRequest) { 
		return BlogPost.builder()
				.title(blogPostRequest.getTitle())
				.subTitle(blogPostRequest.getSubTitle())
				.summary(blogPostRequest.getSummary())
				.postType(PostType.DRAFT)
				.build();
	}
}