package com.chatmessage.controller;

import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.UserService;
import com.chatmessage.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chatmessage.model.Users;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

@WebMvcTest(UserController.class)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController mockUserController;

    @Mock
    private UserRepository mockUserRepository;

    @MockBean
    private UserService mockUserService;


    @Before
    public void setUpUser() throws Exception {
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
        verifyNoInteractions(mockUserRepository);
        verify(mockUserService, times(1)).addUser(user2);

    }

    @Test
    void deleteUsers() throws Exception {
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Baby");

        doNothing().when(mockUserService).deleteUserById(user2.getId());

        mockMvc.perform(delete("/users/delete/" + user2.getId()))
                .andExpect(status().isGone());
        verifyNoInteractions(mockUserRepository);
        verify(mockUserService, times(1)).deleteUserById(user2.getId());
    }

    @Test
    void deleteAllUsers() throws Exception {
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Baby");

        doNothing().when(mockUserService).deleteAllUsers();

        mockMvc.perform(delete("/users/deleteallusers/"))
                .andExpect(status().isGone());
        verifyNoInteractions(mockUserRepository);
        verify(mockUserService, times(1)).deleteAllUsers();
    }



    //FIND USER BY ID
    @Test
    void getUserById() throws Exception {
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Baby");

        Mockito.when(mockUserService.findUserById(user2.getId())).thenReturn(user2);

        mockMvc.perform(get("/users/getuser/" + user2.getId()))
                .andExpect(content().json("{\n" +
                        "        \"id\": \"101\",\n" +
                        "        \"name\": \"Baby\"\n" +
                        "    }"))
                .andExpect(status().isOk());

    }

//ADD USERS

    @Test
    void addManyUsers() throws Exception {
        Users user2 = new Users();
        user2.setId("101");
        user2.setName("Baby");

        List<Users> users = new
                ArrayList<>();
        users.add(user2);
        Mockito.when(mockUserService.addManyUser(users)).thenReturn(users);

        mockMvc.perform(post("/users/addusers")
                .content(om.writeValueAsString(users))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id", is("101")))
                .andExpect(jsonPath("$[0].name", is("Baby")));
        verifyNoInteractions(mockUserRepository);
        verify(mockUserService, times(1)).addManyUser(users);

    }

}


//ADD USER WITH NULL NAME..









