package com.chatmessage.service.impl;

import com.chatmessage.exceptions.MessageNotFoundException;
import com.chatmessage.exceptions.ReceiverNotFoundException;
import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import com.mongodb.util.JSON;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * MESSAGE SERVICES TEST CLASS
 */


@RunWith(MockitoJUnitRunner.class)
@DataMongoTest
class MessageServiceImplTest {

    @InjectMocks
    MessageServiceImpl mockMessageServiceImpl;

    @Mock
    MessageRepository mockMessageRepository;


    /**
     * Testing sending single message
     */

    @Test
    void sendSingleMessage() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);


        Mockito.when(mockMessageRepository.save(message)).thenReturn(message);
        assertEquals(message, mockMessageServiceImpl.sendSingleMessage(message));
    }

    /**
     * Testing sending multiple messages
     */


    @Test
    void sendMultipleMessage() {   //check again not working
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        Message message1 = new Message();
        message1.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);

        Mockito.when(mockMessageRepository.saveAll(messages)).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.sendMultipleMessage(messages));
    }

    /**
     * Testing showing all received messages
     */
    @Test
    void getAllReceivedMessages() {
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

    /**
     * Testing all sent messages
     */

    @Test
    void getAllSentMessages() {

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


    /**
     * Testing estimate messages till end of the day
     */

    @Test
    void estimateDayMessages() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);

        message.setLocalDateTime(LocalDateTime.now());

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double expectedMessagesInDay = (double) (messages.size()) / (now.toSecondOfDay()) * 86400;
        assertEquals(expectedMessagesInDay, mockMessageServiceImpl.estimateDayMessages(), 0.25);
    }

    /**
     * Testing estimate messages till the end of the week
     */

    @Test
    void estimateWeekMessages() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setLocalDateTime(LocalDateTime.now());
        message.setReceiver(user1);

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double expectedMessagesInWeek = (double) (messages.size()) / (now.toSecondOfDay()) * 604800;
        System.out.println(expectedMessagesInWeek);
        assertEquals(expectedMessagesInWeek, mockMessageServiceImpl.estimateWeekMessages(), 0.25);
    }

    /**
     * Testing delete all messages
     */

    @Test
    void deleteAllMessages() {

        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        mockMessageServiceImpl.deleteAllMessages();
        verify(mockMessageRepository, times(1)).deleteAll();
    }


    /**
     * Testing show all messages in the DB
     */
    @Test
    void showAllMessages() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        assertEquals(messages, mockMessageServiceImpl.showAllMessages());
    }

    /**
     * Testing reading contents of message
     */

    @Test
    void readMessagesContent() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);


        Mockito.when(mockMessageRepository.findById(message.getId())).thenReturn(java.util.Optional.of(message));
        assertEquals(message.getContent(), mockMessageServiceImpl.readMyMessage(message.getId()));

    }


    /**
     * Adds a '+' sign to the message id if another message already exists in the DB
     * with the same id.
     */

    @Test
    void sendMessageWithChangedID() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);

        Message message1 = new Message();
        message1.setContent("Hello this is a test message from 101 ro 102");
        message1.setSubject("TestMsg101to102");
        message1.setId("message101to102");
        message1.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Mockito.when(mockMessageRepository.findAll()).thenReturn(messages);
        Mockito.when(mockMessageServiceImpl.sendSingleMessage(message1)).thenReturn(message1);
        System.out.println(message1);
        assertEquals((message1.getId()), message.getId() + " +");
    }


    /**
     * Test for exception of receiver not set.
     */
    @Test
    void receiverNotAddedException() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");

        Mockito.when(mockMessageRepository.findById(message.getId())).thenReturn(java.util.Optional.of(message));
        //message receiver not set

        try {
            mockMessageServiceImpl.sendSingleMessage(message);
            fail();
        } catch (ReceiverNotFoundException ex) {
            assertEquals(ex.getMessage(), "Receiver not named");

        }
    }

    @Test
    void messageDoesNotExist() {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");
        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setReceiver(user1);

        Mockito.when(mockMessageRepository.findById(message.getId())).thenReturn(java.util.Optional.of(message));

        try {
            mockMessageServiceImpl.readMyMessage("Not id message");
            fail();
        } catch (MessageNotFoundException ex) {
            assertEquals(ex.getMessage(), "Message does not exist!");

        }
    }
}

