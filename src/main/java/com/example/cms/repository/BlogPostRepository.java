package com.example.cms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>{

	List<BlogPost> findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime now, PostType scheduled);

}
