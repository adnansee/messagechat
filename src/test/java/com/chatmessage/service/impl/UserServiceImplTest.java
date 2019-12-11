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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
//@AutoConfigureDataMongo
//@EnableMongoRepositories
@DataMongoTest
class UserServiceImplTest {


    @InjectMocks
    UserServiceImpl mockUserService;

    @Mock
    UserRepository mockUserRepository;





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

        System.out.println(mockUserRepository);
        System.out.println(mockUserService);

        Mockito.when(mockUserService.addManyUser(manyUsers)).thenReturn(manyUsers);

        assertEquals(manyUsers, mockUserService.addManyUser(manyUsers));

    }


}