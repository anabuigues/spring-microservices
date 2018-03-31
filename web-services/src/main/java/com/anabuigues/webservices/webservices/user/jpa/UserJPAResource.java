package com.anabuigues.webservices.webservices.user.jpa;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anabuigues.webservices.webservices.user.User;
import com.anabuigues.webservices.webservices.user.UserNotFoundException;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<User> listUsers() {
		return usersRepository.findAll();
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = usersRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user-id}").buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/jpa/users/{user-id}")
	public Resource<User> detailUser(@PathVariable("user-id") int userId) {
		Optional<User> user = usersRepository.findById(userId);

		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + userId);
		}

		// HATEOAS
		Resource<User> resource = new Resource<>(user.get());

		ControllerLinkBuilder linkTo = ControllerLinkBuilder
				.linkTo(methodOn(this.getClass()).listUsers());

		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@DeleteMapping("/jpa/users/{user-id}")
	public void deleteUser(@PathVariable("user-id") int userId) {
		usersRepository.deleteById(userId);
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> listPostByUser(@PathVariable int id) {
		Optional<User> postsByUser = usersRepository.findById(id);

		if (!postsByUser.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		return postsByUser.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id,
			@RequestBody Post post) {
		Optional<User> savedUser = usersRepository.findById(id);

		if (!savedUser.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		User user = savedUser.get();
		post.setUser(user);

		postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{user-id}").buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}
