package com.chatmessage.service.impl;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@DataMongoTest
class MessageServiceImplTest {

    @InjectMocks
    MessageServiceImpl mockMessageServiceImpl;

    @Mock
    MessageRepository mockMessageRepository;

    @Mock
    UserRepository mockUserRepository;


    @Before
    public void testMessagesTime(){
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setLocalDateTime(LocalDateTime.now());
        mockMessageRepository.save(message);
    }



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
        user2.setId("102");
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
    void estimateDayMessages() {
       Message message = new Message();
       message.setContent("Hello this is a test message from 101 ro 102");
       message.setSubject("TestMsg101to102");
       message.setId("message101to102");
       message.setLocalDateTime(LocalDateTime.now());

       List<Message> messages = new ArrayList<>();
       messages.add(message);

       Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
       LocalTime now = LocalTime.now(ZoneId.systemDefault());
       double expectedMessagesInDay = (double) (messages.size()) / (now.toSecondOfDay()) * 86400;
       assertEquals(expectedMessagesInDay , mockMessageServiceImpl.estimateDayMessages(),0.25);
    }

    @Test
    void estimateWeekMessages() {
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setLocalDateTime(LocalDateTime.now());

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double expectedMessagesInWeek = (double) (messages.size()) / (now.toSecondOfDay()) * 604800;
        System.out.println(expectedMessagesInWeek);
        assertEquals(expectedMessagesInWeek , mockMessageServiceImpl.estimateWeekMessages(),0.25);
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
        assertEquals(message.getContent(), mockMessageServiceImpl.readMessages(message));/////
    }

    @Test

    void readMyMessages() {
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Mockito.when(mockMessageRepository.findMessageById(message.getId())).thenReturn(message);
        assertEquals(message.getContent(), mockMessageServiceImpl.readMyMessage(message.getId()));/////
    }


    @Test
    void findReceiverIdAndAllocatedWhenOnlyNameGiven(){

        //Sender
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        //Receiver
        Users user2 = new Users();
        user2.setId("102");
        user2.setName("Dan");

        //Dummy
        Users user3 = new Users();
        user3.setName("Dan");

        List<Users> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user3);
        message.setSender(user2);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);

        Mockito.when(mockMessageServiceImpl.sendSingleMessage(message)).thenReturn(message);
        System.out.println(message);
        System.out.println(message.getReceiver().getId());
        assertEquals(user2.getId(), message.getReceiver().getId());

    }

    @Test
    void findSenderIdAndAllocatedWhenOnlyNameGiven(){
        //Sender
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        //Receiver
        Users user2 = new Users();
        user2.setId("102");
        user2.setName("Dan");

        //Dummy
        Users user3 = new Users();
        user3.setId("102");

        List<Users> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        message.setSender(user3);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);

        Mockito.when(mockMessageServiceImpl.sendSingleMessage(message)).thenReturn(message);
        System.out.println(message);
        System.out.println(message.getSender().getId());
        assertEquals(user2.getId(), message.getSender().getId());
    }

}