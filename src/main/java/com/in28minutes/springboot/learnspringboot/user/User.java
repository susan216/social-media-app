package com.in28minutes.springboot.learnspringboot.user;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

//@JsonFilter("userFilter")- dynamicFiltering
@Entity(name="user_details")
public class User {
	
	protected User() {
		
	}
	
	@Size(min=7, message="id should contain 7 characters")
	//@JsonProperty("SOEID")
	@Id
	private String id;
	
	@Size(min=2, message="name should be of length 2")
	//@JsonIgnore - Static filtering
	private String name;
	private String status;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Post> posts;
	
	
	@PastOrPresent
	private LocalDateTime updatedAt;
	public User(String id, String name, String status, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.updatedAt = updatedAt;
	}
	
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	

	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", status=" + status + ", updatedAt=" + updatedAt + "]";
	}
	
	
	

}
