package com.crr.restful.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

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

    //delete a User
    public User deleteUserById(int id){
        Iterator<User> iterator =  users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
