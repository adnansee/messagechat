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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;

/**
 * MESSAGE CONTROLLER TESTS CLASS
 */

@WebMvcTest(MessageController.class)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
class MessageControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MessageController mockMessageController;

    @Mock
    private MessageRepository mockMessageRepository;

    @MockBean
    private MessageService mockMessageService;


    @Before
    public void setUpMessage() {
        mockMvc = MockMvcBuilders.standaloneSetup(mockMessageController).build();
    }

    /**
     * Testing status ok
     *
     * @throws Exception
     */
    @Test
    void getAllMessagesConnection() throws Exception {
        mockMvc.perform(get("/messages/showallmessages"))
                .andExpect(status().isOk());
        verifyNoInteractions(mockMessageRepository);
    }

    /**
     * Testing sending single message
     *
     * @throws Exception
     */

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

        Mockito.when(mockMessageService.sendSingleMessage(message)).thenReturn(message);

        mockMvc.perform(post("/messages/sendmsg")
                .content(om.writeValueAsString(message))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("message101to102")))
                .andExpect(jsonPath("$.subject", is("TestMsg101to102")))
                .andExpect(jsonPath("$.content", is("Hello this is a test message from 101 ro 102")));
        verifyNoInteractions(mockMessageRepository);

    }

    /**
     * Testing sending multiple messages
     *
     * @throws Exception
     */

    @Test
    void sendMultipleMessages() throws Exception {

        Users user1 = new Users();
        user1.setId("101");
        Users user2 = new Users();
        user2.setId("102");

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 to 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setSender(user1);
        message.setReceiver(user2);

        Message message1 = new Message();
        message1.setContent("Hello this is a test message from 102 to 101");
        message1.setSubject("TestMsg102to101");
        message1.setId("message102to101");
        message1.setSender(user2);
        message1.setReceiver(user1);

        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);

        Mockito.when(mockMessageService.sendMultipleMessage(messages)).thenReturn(messages);

        mockMvc.perform(post("/messages/sendmsgs")
                .content(om.writeValueAsString(messages))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));
        verifyNoInteractions(mockMessageRepository);
    }


    /**
     * Testing read message content searched by message id
     *
     * @throws Exception
     */
    @Test
    void readMessagesById() throws Exception {

        Message message1 = new Message();
        message1.setContent("First Message Of The Day");
        message1.setSubject("First Message Subject");
        message1.setId("101");


        Mockito.when(mockMessageService.readMyMessage(message1.getId())).thenReturn(message1.getContent());

        mockMvc.perform(get("/messages/read/mymessage/" + message1.getId()))
                .andExpect(content().string(message1.getContent()))
                .andExpect(status().isOk());
        verifyNoInteractions(mockMessageRepository);
    }


    /**
     * Testing get all received messages
     *
     * @throws Exception
     */


    @Test
    void getAllReceivedMessages() throws Exception {
        Users user1 = new Users();
        user1.setId("101");
        Users user2 = new Users();
        user2.setId("102");

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 to 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setSender(user2);
        message.setReceiver(user1);

        Message message1 = new Message();
        message1.setContent("Hello this is a test message from 102 to 101");
        message1.setSubject("TestMsg102to101");
        message1.setId("message102to101");
        message1.setSender(user2);
        message1.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message);
        Mockito.when(mockMessageService.getAllReceivedMessages(user1.getId())).thenReturn(messages);

        mockMvc.perform(get("/messages/read/myrecieved/" + user1.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("message102to101")))
                .andExpect(jsonPath("$[0].subject", is("TestMsg102to101")))
                .andExpect(jsonPath("$[1].id", is("message101to102")))
                .andExpect(jsonPath("$[1].subject", is("TestMsg101to102")));
        verifyNoInteractions(mockMessageRepository);
    }

    /**
     * Testing all send messages
     *
     * @throws Exception
     */


    @Test
    void getAllSentMessages() throws Exception {
        Users user1 = new Users();
        user1.setId("101");
        Users user2 = new Users();
        user2.setId("102");

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 to 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setSender(user2);
        message.setReceiver(user1);

        Message message1 = new Message();
        message1.setContent("Hello this is a test message from 102 to 101");
        message1.setSubject("TestMsg102to101");
        message1.setId("message102to101");
        message1.setSender(user2);
        message1.setReceiver(user1);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message);
        Mockito.when(mockMessageService.getAllSentMessages(user2.getId())).thenReturn(messages);

        mockMvc.perform(get("/messages/read/mysent/" + user2.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("message102to101")))
                .andExpect(jsonPath("$[0].subject", is("TestMsg102to101")))
                .andExpect(jsonPath("$[1].id", is("message101to102")))
                .andExpect(jsonPath("$[1].subject", is("TestMsg101to102")));
        verifyNoInteractions(mockMessageRepository);
    }


    /**
     * Testing estimated messages to expect till the end of the day
     *
     * @throws Exception
     */

    @Test
    void estimateDayMessages() throws Exception {

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 to 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setLocalDateTime(LocalDateTime.now());
        List<Message> messages = new ArrayList<>();
        messages.add(message);


        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double expectedMessagesInDay = (double) (messages.size()) / (now.toSecondOfDay()) * 86400;
        System.out.println(expectedMessagesInDay);
        Mockito.when(mockMessageService.estimateDayMessages()).thenReturn(expectedMessagesInDay);
        System.out.println(expectedMessagesInDay);
        mockMvc.perform(get("/messages/estimateday"))
                .andExpect(content().string(String.valueOf(expectedMessagesInDay)))
                .andExpect(status().isOk());
        verifyNoInteractions(mockMessageRepository);
    }

    /**
     * Testing estimated messages to expect till the end of the week
     *
     * @throws Exception
     */

    @Test
    void estimateWeekMessages() throws Exception {

        Message message = new Message();
        message.setContent("Hello this is a test message from 101 to 102");
        message.setSubject("TestMsg101to102");
        message.setId("message101to102");
        message.setLocalDateTime(LocalDateTime.now());

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double expectedMessagesInWeek = (double) (messages.size()) / (now.toSecondOfDay()) * 604800;
        System.out.println(expectedMessagesInWeek);
        Mockito.when(mockMessageService.estimateWeekMessages()).thenReturn(expectedMessagesInWeek);
        System.out.println(mockMessageService.estimateWeekMessages());

        mockMvc.perform(get("/messages/estimateweek"))
                .andExpect(content().string(String.valueOf(expectedMessagesInWeek)))
                .andExpect(status().isOk());
        verifyNoInteractions(mockMessageRepository);
    }

    /**
     * Testing delete all messages
     *
     * @throws Exception
     */

    @Test
    void deleteAllMessages() throws Exception {
        Message message1 = new Message();
        message1.setContent("First Message Of The Day");
        message1.setSubject("First Message Subject");
        message1.setId("101");

        doNothing().when(mockMessageService).deleteAllMessages();

        mockMvc.perform(delete("/messages/deleteallmessages"))
                .andExpect(status().isGone());
        verifyNoInteractions(mockMessageRepository);
        verify(mockMessageService, times(1)).deleteAllMessages();
    }

    /**
     * Testing show all messages in the DB
     *
     * @throws Exception
     */

    @Test
    void showAllMessagesInDB() throws Exception {
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

        Mockito.when(mockMessageService.showAllMessages()).thenReturn(messages);

        mockMvc.perform(get("/messages/showallmessages"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        verifyNoInteractions(mockMessageRepository);
    }
}
