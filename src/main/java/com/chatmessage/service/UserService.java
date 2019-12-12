package com.chatmessage.service;


import com.chatmessage.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<Users> getAllUsers();
    Users addUser(Users user);
    List<Users> addManyUser(List<Users> users);
    void deleteAllUsers();
    void deleteUserById(String user);
    Users findUserById(String user);
}
