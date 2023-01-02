package com.in28minutes.springboot.learnspringboot.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> usersList = new ArrayList<User>();
	
	static {		
		usersList.add(new User("DE95568", "Deepthi", "true", LocalDateTime.now()));
		usersList.add(new User("DE95569", "Esukapalli", "true", LocalDateTime.now()));
		usersList.add(new User("DE95570", "Deepthi E", "true", LocalDateTime.now()));
	}

	public List<User> findAllUsers() {
		return usersList;
	}

	public User saveUsers(User user) {
		usersList.add(user);
		return user;		
	}

	public User findUser(String id) {
		Predicate<? super User> predicate = user -> user.getid().equalsIgnoreCase(id);
		return usersList.stream().filter(predicate).findFirst().orElse(null);		
	}
	
	
	public void deleteUser(String id) {
		Predicate<? super User> predicate = user -> user.getid().equalsIgnoreCase(id);
		usersList.removeIf(predicate);		
	}

}
