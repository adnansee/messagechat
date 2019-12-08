package com.chatmessage.Seeder;


import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.chatmessage.repository.UserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
/**
 * Simply enters some data in command line to test
 */
@Component
public class DBSeeder implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;



    /**
     * This class simply enters some data via command line to test
    *
    **/

    @Override
    public void run(String... args) throws Exception {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Eddy");

        Users user2 = new Users();
        user2.setId("102");
        user2.setName("Dan");


        Message m1 = new Message();
        m1.setId("message1");
        m1.setContent("Subject Message No.1 from User 1 (101-Eddy) to user 2 (102-Dan)");
        m1.setSender(user1);
        m1.setReceiver(user2);
        m1.setLocalDateTime(LocalDateTime.now());
        m1.setSubject("Hello User 2 Dan from Eddy");

        Message m2 = new Message();
        m2.setId("message2");
        m2.setContent("Subject Message No.2 from User 2 (102) to user 1 (101)");
        m2.setSender(user2);
        m2.setReceiver(user1);
        m2.setLocalDateTime(LocalDateTime.now());
        m2.setSubject("Hello User 1 Eddy from Dan");


        List<Users> users = Arrays.asList(user1,user2);
        this.userRepository.saveAll(users);
        List<Message> messages = Arrays.asList(m1,m2);
        this.messageRepository.saveAll(messages);
         }


}
