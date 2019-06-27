package com.practice.springbootstarter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUser() {
        return userDaoService.findAll();
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUserUsingJPA() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveOne(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id- " + id);

        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @GetMapping("/jpa/users/{id}")
    public Optional<User> retrieveOneUsingJPA(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " is not present");
        } else {
            return user;
        }
    }

    @PostMapping("/users/save")
    public ResponseEntity<Object> save(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/jpa/users/save")
    public ResponseEntity<Object> saveUsingJPA(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/users/delete/{id}")
    public User deleteById(@PathVariable int id) {
        User user = userDaoService.deleteById(id);

        if (user == null)
            throw new UserNotFoundException("id- " + id);

        return user;
    }

    @DeleteMapping("/jpa/users/delete/{id}")
    public void deleteByIdUsingJPA(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
