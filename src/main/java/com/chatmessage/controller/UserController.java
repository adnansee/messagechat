package com.chatmessage.controller;

import com.chatmessage.model.Users;
import com.chatmessage.service.UserService;
import com.chatmessage.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatmessage.repository.UserRepository;
import java.util.List;

/**
 *  PLease consult the UserServiceImpl Class to view detailed documentation
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET, value = "/getallusers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/adduser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/addusers")
    public ResponseEntity<List<Users>> addManyUser(@RequestBody List<Users> users) {
        userService.addManyUser(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteallusers")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.GONE);
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{user}")
    private ResponseEntity<Void> deleteUser(@PathVariable("user") Users user) {
       userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
