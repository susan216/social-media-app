package com.in28minutes.springboot.learnspringboot.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

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

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	
	private UserDaoService service = new UserDaoService();
	
	public UserController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public MappingJacksonValue getAllUsers() {
		List<User> allUsers = service.findAllUsers();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(allUsers);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","status");;
		FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", filter);
		mappingJacksonValue.setFilters(filters );
		return mappingJacksonValue;
		//mappingJacksonValue - for dynamic filtering
		
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> getUserById(@PathVariable String id) {
		User user = service.findUser(id);
		System.out.println(user);
		if(user==null) {
			throw new UserNotFoundException("id " + id);
		}
		EntityModel<User> entityModel = EntityModel.of(user);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
		//return user;
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		 User savedUser = service.saveUsers(user);
		 URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				 path("/{id}").buildAndExpand(savedUser.getid())
				 .toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable String id) {
		service.deleteUser(id);		
	}

}
