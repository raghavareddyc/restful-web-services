package com.crr.restful.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    UserDaoService service;

    @Autowired
    private MessageSource bundleMessageSource;

    //get all users
    @GetMapping(path = "/users")
    public List<User> getUsers(){
        return service.getAllUsers();
    }

    //get one user
    @GetMapping(path="/users/{id}")
    public EntityModel<User> getOneUser(@PathVariable int id){
        User user = service.getUser(id);
        if(user == null) throw new UserNotFoundException("id - " + id);

        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        EntityModel<User> resource = EntityModel.of(user);

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).getUsers());

        resource.add(linkTo.withRel("all-users"));
        return resource;
    }


    //create a new user
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user) {
       User savedUser = service.createUser(user);

       //Created
       // /user/{id}

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //get one user
    @DeleteMapping(path="/users/{id}")
    public void deleteOneUser(@PathVariable int id){
        User user = service.deleteUserById(id);
        if(user == null) throw new UserNotFoundException("id - " + id);
    }

    @GetMapping(path="/ref-internationalization")
    public String sampleInternationalization() {
        return bundleMessageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping(path="/locale")
    public String locale(){
        return "locale";
    }
}
