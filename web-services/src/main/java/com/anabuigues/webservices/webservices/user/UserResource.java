package com.anabuigues.webservices.webservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService usersDao;

	// retrieve all users
	@GetMapping("/users")
	public List<User> listUsers() {
		return usersDao.findAll();
	}

	// retrieve users
	@GetMapping("/users/{user-id}")
	public User detailUser(@PathVariable("user-id") int userId) {
		return usersDao.findOne(userId);
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = usersDao.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user-id}").buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}
