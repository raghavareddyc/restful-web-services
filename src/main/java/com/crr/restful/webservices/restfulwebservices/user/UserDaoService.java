package com.crr.restful.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static Integer userCount = 3;
    static {
        users.add(new User(1, "CRR", new Date()));
        users.add(new User(2, "DVP", new Date()));
        users.add(new User(3, "GS", new Date()));
    }

    //getAllUsers
    public List<User> getAllUsers() {
        return users;
    }

    //get Single User
    public User getUser(int id){
        for(User user: users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    //create a new user
    public User createUser(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }
}
