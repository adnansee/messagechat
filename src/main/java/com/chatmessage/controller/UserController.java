package com.chatmessage.controller;

import com.chatmessage.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chatmessage.repository.UserRepository;
import java.util.List;

/**
 *
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /** SHOW ALL USERS ADDED
     * This methos shows all users that are present in the MonngoDB
     * @param
     * @return {List<Users>} all users that are added to the MongoDB
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getallusers")
    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
    }

    /** ADD ONE USER TO THE DATABASE
     * This method adds a user to the MongoDB
     * @param {User}
     * @return Http status code
     */
    @RequestMapping(method = RequestMethod.POST, value = "/adduser")
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /** ADD MULTIPLE USERS TO THE DATABASE
     * This method adds multiple user to the MongoDB
     * @param {List<User>}
     * @return Http status code
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addusers")
    public ResponseEntity<List<Users>> addManyUser(@RequestBody List<Users> users) {
        userRepository.saveAll(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    /** This method deletes all users from the MongoDB
     * @param
     * @return Http status code
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteallusers")
    public ResponseEntity<Void> deleteAllUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    /** This method deletes a user to the MongoDB
     * @param {User}
     * @return Http status code
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{user}")
    private ResponseEntity<Void> deleteUser(@PathVariable("user") Users user) {
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
