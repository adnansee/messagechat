package com.chatmessage.service.impl;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import org.junit.AfterClass;
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




@RunWith(MockitoJUnitRunner.class)
@DataMongoTest
class MessageServiceImplTest {

    @InjectMocks
    MessageServiceImpl mockMessageServiceImpl;

    @Mock
    MessageRepository mockMessageRepository;

    @Mock
    UserRepository mockUserRepository;



//DONE
    @Test
    void sendSingleMessage() {

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Message message1 = new Message();
        Mockito.when(mockMessageRepository.save(message)).thenReturn(message);
        assertEquals(message, mockMessageServiceImpl.sendSingleMessage(message));

    }

    @Test
    void sendMultipleMessage() {   //check again not working
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Message message1 = new Message();

        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);

        Mockito.when(mockMessageRepository.saveAll(messages)).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.sendMultipleMessage(messages));

    }
    @Test
    void getAllMessages() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Dan");

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        message.setSender(user2);

        Message message1 = new Message();
        message1.setContent("Hello");
        message1.setSubject("TestMsg102to101");
        message1.setId("message102to101");
        message1.setReceiver(user2);
        message1.setSender(user1);

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        List<Message> messages1 = new ArrayList<>();
        messages1.add(message1);

        Mockito.when(mockMessageRepository.findAllByReceiver_Id(user1.getId())).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.getAllReceivedMessages(user1.getId()));

    }

    @Test
    void readSentMessages() {

        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Dan");

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        message.setSender(user2);

        Message message1 = new Message();
        message1.setContent("Hello");
        message1.setSubject("TestMsg102to101");
        message1.setId("message102to101");
        message1.setReceiver(user2);
        message1.setSender(user1);

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        List<Message> messages1 = new ArrayList<>();
        messages1.add(message1);

        Mockito.when(mockMessageRepository.findAllBySender_Id(user2.getId())).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.getAllSentMessages(user2.getId()));
    }

    @Test

    void readMyMessages() {
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Mockito.when(mockMessageRepository.findMessageById(message.getId())).thenReturn(message);
        assertEquals(message.getContent(), mockMessageServiceImpl.readMyMessage(message.getId()));
    }

   @Test
    void estimateDayMessages() {
    }

    @Test
    void estimateWeekMessages() {
    }

    @Test
    void deleteAllMessages() {
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        mockMessageServiceImpl.deleteAllMessages();
        verify(mockMessageRepository, times(1)).deleteAll();
    }

    @Test
    void showAllMessages() {

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.showAllMessages());
    }

   @Test
        //without id is it really needed
    void readMyMessage() {
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Mockito.when(mockMessageRepository.findMessageById(message.getId())).thenReturn(message);
        assertEquals(message.getContent(), mockMessageServiceImpl.readMessages(message));
    }
}