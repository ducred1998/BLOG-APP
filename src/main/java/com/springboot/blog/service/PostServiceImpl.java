package com.springboot.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto) {
		// Convert DTO to Entity
		Post post = mapToEntity(postDto);
		// saving post
		Post newPost = postRepository.save(post);
		// Convert entity to DTO
		PostDto postResponse = mapToDto(newPost);
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		// GET POST ID FROM DATABASE
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(Long id) {
		// GET POST ID FROM DATABASE
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	// convert Entity into DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());

		return postDto;
	}

	// convert DTO into Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;
	}

}
