package com.in28minutes.springboot.learnspringboot.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.springboot.learnspringboot.user.Post;
import com.in28minutes.springboot.learnspringboot.user.User;
import com.in28minutes.springboot.learnspringboot.user.UserDaoService;
import com.in28minutes.springboot.learnspringboot.user.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserJPAController {
	
	
	private PostJPARepository postRepository;
	private UserJPARepository repository;
	
	public UserJPAController(UserJPARepository repository, PostJPARepository postRepository) {
		this.postRepository = postRepository;
		this.repository = repository;
	}
	
	@GetMapping("/jpa/users")
	public MappingJacksonValue getAllUsers() {
		List<User> allUsers = repository.findAll();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(allUsers);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","status");;
		FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", filter);
		mappingJacksonValue.setFilters(filters );
		return mappingJacksonValue;
		//mappingJacksonValue - for dynamic filtering
		
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> getUserById(@PathVariable String id) {
		Optional<User> user = repository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("id " + id);
		}
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
		//return user;
		
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		 User savedUser = repository.save(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				 path("/{id}").buildAndExpand(savedUser.getid())
				 .toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable String id) {
		repository.deleteById(id);		
	}
	
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsByUser(@PathVariable String id) {
		Optional<User> user = repository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("id " + id);
		}
		
		return user.get().getPosts();
	}
	
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> addPostToUser(@PathVariable String id,@Valid @RequestBody Post post) {
		 
		Optional<User> user = repository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("id " + id);
		}
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				 path("/{id}").buildAndExpand(savedPost.getId())
				 .toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts/{post_id}")
	public Post retrievePostByUser(@PathVariable String id,@PathVariable Integer post_id) {
		Optional<User> user = repository.findById(id);
		if(user==null) {
			throw new UserNotFoundException("id " + id);
		}
		
		return postRepository.findById(post_id).get();
	}

}
