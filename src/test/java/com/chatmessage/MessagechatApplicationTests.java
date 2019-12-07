package com.chatmessage;

import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@ContextConfiguration()

class MessagechatApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;


    @Test
    void contextLoads() throws Exception {

        Mockito.when(userRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/getallusers")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.println(mvcResult.getResponse());

        Mockito.verify(userRepository).findAll();
    }


}
