package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	List<PostDto> getAllPosts();
	
	PostDto getPostById(Long id);
	
	PostDto updatePost(PostDto postDto, Long id);
	
	void deletePostById(Long id);
}
