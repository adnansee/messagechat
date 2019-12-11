package com.chatmessage.service.impl;

import com.chatmessage.model.Users;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;




@RunWith(MockitoJUnitRunner.class)

class UserServiceImplTest {

   ///@InjectMocks
    //@Qualifier
    //@Autowired
    // @InjectMocks
    @Mock
    UserRepository userRepository;

    //@InjectMocks
    //@Qualifier
    //@Autowired
     @InjectMocks
    //@Mock
    //@MockBean
    UserService userService;



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

        System.out.println(userRepository);
        System.out.println(userService);
      Mockito.when(userService.addManyUser(manyUsers)).thenReturn(manyUsers);

      // Mockito.when(userRepository.saveAll(manyUsers)).thenReturn(manyUsers);


}}