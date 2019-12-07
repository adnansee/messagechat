package com.chatmessage.service;


import com.chatmessage.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface UserService {

    List<Users> getAllUsers();
    Users addUser(Users user);
    List<Users> addManyUser(List<Users> users);
    void deleteAllUsers();
    void deleteUser(Users user);
}
