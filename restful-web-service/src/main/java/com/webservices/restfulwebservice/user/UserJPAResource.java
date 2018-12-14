package com.webservices.restfulwebservice.user;

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

@RestController
public class UserJPAResource {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> listAllUsers(){
		return userRepository.findAll();
	}
//HATEOAS- Hypermedia As The Engine Of Application State
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retriveUser(@PathVariable int id) {
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNorFoundException("id- "+id);
		}
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder link =ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).listAllUsers());
		resource.add(link.withRel("All-Users"));
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/jpa/users/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getPosts(@PathVariable int id){

		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNorFoundException("id- "+id);
		}
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> savePost(@PathVariable int id,@RequestBody Post post) {
		Optional<User> users = userRepository.findById(id);
		if(!users.isPresent()) {
			throw new UserNorFoundException("id- "+id);
		}
		User user = users.get();
		post.setUser(user);
		Post savedPost = postRepository.save(post);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
