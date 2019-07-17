package com.practice.springbootstarter.controller;

import com.practice.springbootstarter.dao.UserDaoService;
import com.practice.springbootstarter.exception.UserNotFoundException;
import com.practice.springbootstarter.model.Post;
import com.practice.springbootstarter.model.User;
import com.practice.springbootstarter.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
@RequestMapping("/users")
@Api(value = "/users")
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "RetrieveAllUsersUsingJPA", nickname = "Retrieve all database users using JPA",
            produces = "application/json", httpMethod = "GET")
    @ApiResponse(code = 200, message = "Successful response send")
    @GetMapping("/jpa")
    public List<User> retrieveAllUserUsingJPA() {
        return userRepository.findAll();
    }

    @ApiOperation(value = "RetrieveOne", nickname = "Retrieve user by id from static users list",
            produces = "application/json", httpMethod = "GET")
    @ApiResponse(code = 200, message = "Successful response send")
    @GetMapping("/{id}")
    public Resource<User> retrieveOne(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id- " + id);

        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @ApiOperation(value = "RetrieveAllUsers", nickname = "Retrieve all static users",
            produces = "application/json", httpMethod = "GET")
    @ApiResponse(code = 200, message = "Successful response send")
    @GetMapping
    public List<User> retrieveAllUser() {
        return userDaoService.findAll();
    }

    @ApiOperation(value = "RetrieveOneUsingJPA", nickname = "Retrieve user by id from database using JPA",
            produces = "application/json", httpMethod = "GET")
    @ApiResponse(code = 200, message = "Successful response send")
    @GetMapping("/jpa/{id}")
    public Optional<User> retrieveOneUsingJPA(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with id " + id + " is not present");
        } else {
            return user;
        }
    }

    @ApiOperation(value = "Save", nickname = "Create new static user",
            produces = "application/json", httpMethod = "POST")
    @ApiResponse(code = 201, message = "Successful response send", response = User.class)
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "SaveUsingJPA", nickname = "Create new database user",
            produces = "application/json", httpMethod = "POST")
    @ApiResponse(code = 201, message = "Successful response send", response = User.class)
    @PostMapping("/jpa/save")
    public ResponseEntity<Object> saveUsingJPA(@Valid @RequestBody User user) {
        for (Post post : user.getPosts()) {
            post.setUser(user);
        }
        User savedUser = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "DeleteById", nickname = "Delete static user by id",
            produces = "application/json", httpMethod = "DELETE")
    @ApiResponse(code = 200, message = "Successful response send")
    @DeleteMapping("/delete/{id}")
    public User deleteById(@PathVariable int id) {
        User user = userDaoService.deleteById(id);

        if (user == null)
            throw new UserNotFoundException("id- " + id);

        return user;
    }

    @ApiOperation(value = "DeleteByIdUsingJPA", nickname = "Delete database user by id",
            produces = "application/json", httpMethod = "DELETE")
    @ApiResponse(code = 200, message = "Successful response send")
    @DeleteMapping("/jpa/delete/{id}")
    public void deleteByIdUsingJPA(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
