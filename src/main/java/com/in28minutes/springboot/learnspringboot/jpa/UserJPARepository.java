package com.in28minutes.springboot.learnspringboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.springboot.learnspringboot.user.User;

public interface UserJPARepository extends JpaRepository<User, String>{

}
