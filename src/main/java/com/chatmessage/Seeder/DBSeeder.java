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

@Component
public class DBSeeder {/*implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;



    @Override
    public void run(String... args) throws Exception {
        Users user1 = new Users();
        user1.setId("101");
        user1.setName("Eddy");

        Users user2 = new Users();
        user2.setId("102");
        user2.setName("Baby");


        Message m1 = new Message();
        m1.setContent("HELLO ricky");
        m1.setSender(user1);
        m1.setReceiver(user2);
        m1.setLocalDateTime(LocalDateTime.now());
        m1.setSubject("hello subject");


//        this.userRepository.deleteAll();

        List<Users> users = Arrays.asList(user1,user2);
        this.userRepository.saveAll(users);
        List<Message> messages = Arrays.asList(m1);
        this.messageRepository.saveAll(messages);
         }
*/

}
