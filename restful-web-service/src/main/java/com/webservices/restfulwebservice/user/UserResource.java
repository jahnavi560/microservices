package com.webservices.restfulwebservice.user;

import java.net.URI;
import java.util.List;
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
public class UserResource {
	
	@Autowired
	private UserDao dao;
	
	@GetMapping("/users")
	public List<User> listAllUsers(){
		return dao.findAll();
	}
//HATEOAS- Hypermedia As The Engine Of Application State
	@GetMapping("/users/{id}")
	public Resource<User> retriveUser(@PathVariable int id) {
		
		User user = dao.findOne(id);
		if(user == null) {
			throw new UserNorFoundException("id- "+id);
		}
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder link =ControllerLinkBuilder.
				linkTo(ControllerLinkBuilder.methodOn(this.getClass()).listAllUsers());
		resource.add(link.withRel("All-Users"));
		
		return resource;
	}
	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User savedUser = dao.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@DeleteMapping("/users/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		
		User user = dao.deleteById(id);
		if(user == null) {
			throw new UserNorFoundException("id- "+id);
		}
	}
}
