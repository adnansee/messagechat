package com.chatmessage.service.impl;

import com.chatmessage.model.Message;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

//try more
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
//@EnableAutoConfiguration
//@EnableMongoRepositories
//@AutoConfigureDataMongo
@DataMongoTest
class MessageServiceImplTest {

    @Mock
    MessageRepository mockMessageRepository;

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    MessageServiceImpl mockMessageService;


    @Test
    void sendSingleMessage() {

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Mockito.when(mockMessageService.sendSingleMessage(message)).thenReturn(message);

        System.out.println(mockMessageService+"------>>>>>>>>>>>>>>>>>>>>------------->>>>>>");
        System.out.println(mockMessageRepository+"------>>>>>>>>>>>>>>>>>>>>------------->>>>>>");
        System.out.println(mockUserRepository+"------>>>>>>>>>>>>>>>>>>>>------------->>>>>>");
    }

    @Test
    void sendMultipleMessage() {
    }

    @Test
    void getAllMessages() {
    }

    @Test
    void readSentMessages() {
    }

    @Test
    void readMessages() {
    }

    @Test
    void estimateDayMessages() {
    }

    @Test
    void estimateWeekMessages() {
    }

    @Test
    void deleteAllMessages() {
    }

    @Test
    void showAllMessages() {
    }

    @Test
    void readMyMessage() {
    }
}