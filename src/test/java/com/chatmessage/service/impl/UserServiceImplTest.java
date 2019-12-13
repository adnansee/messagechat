package com.chatmessage.service.impl;

import com.chatmessage.model.Users;
import com.chatmessage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * USER SERVICES TEST CLASS
 *
 */

@RunWith(MockitoJUnitRunner.class)
@DataMongoTest
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl mockUserServiceImpl;

    @Mock
    UserRepository mockUserRepository;

    /** 
     * Test adding one user
     */
    @Test
    void addUser() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Dan");

        Mockito.when(mockUserRepository.save(user1)).thenReturn(user1);
        assertEquals(user1, mockUserServiceImpl.addUser(user1));
    }

    /**
     * Test adding many users
     */
    @Test
    void addManyUser() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Dan");

        List<Users> manyUsers = new ArrayList<>();
        manyUsers.add(user1);
        manyUsers.add(user2);

        Mockito.when(mockUserRepository.saveAll(manyUsers)).thenReturn(manyUsers);
        assertEquals(manyUsers, mockUserServiceImpl.addManyUser(manyUsers));
    }

    /**
     * Test getting one user
     */
    @Test
    void getUserById() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        Mockito.when(mockUserRepository.findUsersById(user1.getId())).thenReturn(user1);
        assertEquals(user1, mockUserServiceImpl.findUserById(user1.getId()));
    }

    /**
     * Test getting all users
     */

    @Test
    void getAllUsers() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        List<Users> manyUsers = new ArrayList<>();
        manyUsers.add(user1);
        Mockito.when(mockUserRepository.findAll()).thenReturn(manyUsers);
        assertEquals(manyUsers, mockUserServiceImpl.getAllUsers());
    }

    /**
     * Test deleting one user by id
     */
    @Test
    void deleteUserById() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        Mockito.when(mockUserRepository.findUsersById(user1.getId())).thenReturn(user1);
        mockUserServiceImpl.deleteUserById(user1.getId());
        verify(mockUserRepository, times(1)).deleteById(user1.getId());
    }

    /**
     * Test deleting all users
     */
    @Test
    void deleteAllUsers() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        List<Users> users = new ArrayList<>();
        users.add(user1);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        mockUserServiceImpl.deleteAllUsers();
        verify(mockUserRepository, times(1)).deleteAll();
    }

}