package com.crr.restful.webservices.restfulwebservices.user;

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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAController {

    @Autowired
    private UserDaoService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    //get all users
    @GetMapping(path = "/jpa/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    //get one user
    @GetMapping(path="/jpa/users/{id}")
    public EntityModel<User> getOneUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("id - " + id);

        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        EntityModel<User> resource = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).getUsers());

        resource.add(linkTo.withRel("all-users"));
        return resource;
    }


    //create a new user
    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user) {
       User savedUser = userRepository.save(user);

       //Created
       // /user/{id}

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //get one user
    @DeleteMapping(path="/jpa/users/{id}")
    public void deleteOneUser(@PathVariable int id){
        userRepository.deleteById(id);

    }

    @GetMapping(path="/jpa/ref-internationalization")
    public String sampleInternationalization() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping(path="/jpa/locale")
    public String locale(){
        return "locale";
    }
}
