package com.chatmessage.controller;

import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.chatmessage.model.Users;
import org.mockito.Mockito;

import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

@WebMvcTest(UserController.class)//FINALLY SOMETHING WORKS
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc

@ActiveProfiles("test")
class UserControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private UserController mockUserController;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean

    private UserService mockUserService;


    @Before
    public void setUpUser() throws Exception {

        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        List<Users> users = new
                ArrayList<>();
        users.add(user1);


       // Mockito.when(mockUserController.getAllUsers()).thenReturn((ResponseEntity<List<Users>>) users);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);

        mockMvc = MockMvcBuilders.standaloneSetup(mockUserController).build();

    }

    @Test
    void testConnection() throws Exception {
        mockMvc.perform(get("/users/getallusers"))
                .andExpect(status().isOk());
    }



    @Test
    void getAllUsers() throws Exception {

        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Baby");

        List<Users> users = new
                ArrayList<>();
        users.add(user1);
        Mockito.when(mockUserService.getAllUsers()).thenReturn(users);


        mockMvc.perform(get("/users/getallusers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("101")))
                .andExpect(jsonPath("$[0].name", is("Baby")));
    }

    @Test
    void addUser() throws Exception {
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Baby");

        List<Users> users = new
                ArrayList<>();
        users.add(user2);
        Mockito.when(mockUserRepository.save(user2)).thenReturn(user2);

        mockMvc.perform(post("/users/adduser")
                .content(om.writeValueAsString(user2))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("101")))
                .andExpect(jsonPath("$.name", is("Baby")));

    }

    @Test
    void deleteAllUsers() {
    }
}