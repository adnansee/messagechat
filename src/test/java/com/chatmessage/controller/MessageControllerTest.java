package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.service.MessageService;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
//@SpringBootTest
@ActiveProfiles("test")
        //FINALLY SOMETHING WORKS
class MessageControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private MessageController messageController;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private MessageService messageService;


    @Before
    public void setUpMessage() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();

    }

    @Test
    void getAllMessages() throws Exception {
        mockMvc.perform(get("/messages/showallmessages"))
                .andExpect(status().isOk());
    }


    @Test
    void sendSingleMessage() throws Exception {

        Users user1 = new Users();
        user1.setId("101");
        Users user2 = new Users();
        user2.setId("102");


        Message message = new Message();
        message.setContent("Hello this is a test message from 101 ro 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setSender(user1);
        message.setReceiver(user2);

        Mockito.when(messageRepository.save(message)).thenReturn(message);

        mockMvc.perform(post("/messages/sendmsg")
                .content(om.writeValueAsString(message))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(" {\n" +
                        "            \"id\": \"message101to102\",\n" +
                        "                \"subject\": \"TestMsg101to102\",\n" +
                        "                \"content\": \"Hello this is a test message from 101 ro 102\",\n" +
                        "                \"receiver\": {\n" +
                        "            \"id\": \"102\",\n" +
                        "                    \"name\": null\n" +
                        "        },\n" +
                        "            \"sender\": {\n" +
                        "            \"id\": \"101\",\n" +
                        "                    \"name\": null\n" +
                        "        },\n" +
                        "            \"localDateTime\": null\n" +
                        "        }"));



               /* .andExpect(jsonPath("$.id", is("message101to102")))
                .andExpect(jsonPath("$.subject", is("TestMsg101to102")))
                .andExpect(jsonPath("$.content", is("Hello this is a test message from 101 ro 102")))
                .andExpect(jsonPath("$.receiver", is("102")))
                .andExpect(jsonPath("$.sender", is("101")));*/


    }


    @Test
    void readSentMessages() throws Exception {
        Message message1 = new Message();
        message1.setContent("First Message Of The Day");
        message1.setSubject("First Message Subject");
        message1.setId("FirstMessage1");

        Message message2 = new Message();
        message2.setContent("Second Message Of The Day");
        message2.setSubject("Second Message Subject");
        message2.setId("FirstMessage2");

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        Mockito.when(messageService.showAllMessages()).thenReturn(messages);

        mockMvc.perform(get("/messages/showallmessages"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void readMessages() throws Exception {
    }


    @Test
    void estimateDayMessages() throws Exception {

    }
}