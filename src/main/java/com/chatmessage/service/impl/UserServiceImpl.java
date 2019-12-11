package com.chatmessage.service.impl;

import com.chatmessage.model.Users;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /** SHOW ALL USERS ADDED
     * This methos shows all users that are present in the MonngoDB
     * @param
     * @return {List<Users>} all users that are added to the MongoDB
     */
    @Override
    public List<Users> getAllUsers() {
        return this.userRepository.findAll();
    }

    /** ADD ONE USER TO THE DATABASE
     * This method adds a user to the MongoDB
     * @param {User}
     * @return Http status code
     */
    @Override
    public Users addUser(Users user) {
        userRepository.save(user);
        return user;
    }

    /** ADD MULTIPLE USERS TO THE DATABASE
     * This method adds multiple user to the MongoDB
     * @param {List<User>}
     * @return Http status code
     */
    @Override
    public List<Users> addManyUser(List<Users> users) {
        userRepository.saveAll(users);
        return users;
    }

    /** This method deletes all users from the MongoDB
     * @param
     * @return Http status code
     */
    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    /** This method deletes a user to the MongoDB
     * @param {User}
     * @return Http status code
     */
    @Override
    public void deleteUser(Users user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteUserById(String user) {
       //Optional<Users> u = userRepository.findById(user);
        userRepository.deleteById(user);
    }

    @Override
    public Users findUserById(String user) {
        return userRepository.findUsersById(user);
    }


}
