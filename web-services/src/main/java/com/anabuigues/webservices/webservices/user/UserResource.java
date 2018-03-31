package com.anabuigues.webservices.webservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@GetMapping("/users")
	public List<User> listUsers() {
		return usersDao.findAll();
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = usersDao.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user-id}").buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/users/{user-id}")
	public User detailUser(@PathVariable("user-id") int userId) {
		User user = usersDao.findOne(userId);

		if (user == null) {
			throw new UserNotFoundException("id-" + userId);
		}

		return user;
	}

	@DeleteMapping("/users/{user-id}")
	public void deleteUser(@PathVariable("user-id") int userId) {
		User user = usersDao.deleteById(userId);

		if (user == null) {
			throw new UserNotFoundException("id-" + userId);
		}
	}

}
