package com.in28minutes.springboot.learnspringboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.springboot.learnspringboot.user.Post;

public interface PostJPARepository extends JpaRepository<Post, Integer>{

}
