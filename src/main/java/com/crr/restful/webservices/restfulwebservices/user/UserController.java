package com.crr.restful.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserDaoService service;

    //get all users
    @GetMapping(path = "/users")
    public List<User> getUsers(){
        return service.getAllUsers();
    }

    //get one user
    @GetMapping(path="/users/{id}")
    public User getOneUser(@PathVariable int id){
        User user = service.getUser(id);
        if(user == null) throw new UserNotFoundException("id - " + id);
        return user;
    }


    //create a new user
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
       User savedUser = service.createUser(user);

       //Created
       // /user/{id}

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
