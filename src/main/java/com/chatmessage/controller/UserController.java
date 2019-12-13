package com.chatmessage.controller;

import com.chatmessage.model.Users;
import com.chatmessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST CONTROLLER FOR USER SERVICES
 * PLease consult the UserServiceImpl Class to view detailed documentation
 */

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    //GET SINGLE USER BY ID
    @RequestMapping(method = RequestMethod.GET, value = "/getuser/{user_id}")
    public ResponseEntity<Users> getUsersById(@PathVariable("user_id") String user_id) {
        Users users = userService.findUserById(user_id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //GET ALL USERS
    @RequestMapping(method = RequestMethod.GET, value = "/getallusers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //ADD USER
    @RequestMapping(method = RequestMethod.POST, value = "/adduser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //AND MULTIPLE USERS
    @RequestMapping(method = RequestMethod.POST, value = "/addusers")
    public ResponseEntity<List<Users>> addManyUser(@RequestBody List<Users> users) {
        userService.addManyUser(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    //DELETE ALL USERS
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteallusers")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    //DELETE SINGLE USER BY ID
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{user_id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("user_id") String user) {
        userService.deleteUserById(user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
