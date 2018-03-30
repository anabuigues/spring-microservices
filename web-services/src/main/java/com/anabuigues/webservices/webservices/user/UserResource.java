package com.anabuigues.webservices.webservices.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService usersDao;
	
	//retrieve all users
	@GetMapping("/users")
	public List<User> listUsers(){
		return usersDao.findAll();
	}
	
	//retrieve users
	@GetMapping("/users/{userId}")
	public User detailUser(@PathVariable int userId) {
		return usersDao.findOne(userId);
	}	
}
