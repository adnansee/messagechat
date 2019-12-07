package com.chatmessage.service.impl;

import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class MessageServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void sendSingleMessage() {
    }

    @Test
    void getAllMessages() {
    }

    @Test
    void readMessages() {
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
}