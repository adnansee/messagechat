package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    //SEND MESSAGES    NOT WORKING WITH AUTOMATIC DATE/TIME
    @RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setLocalDateTime(LocalDateTime.now());
        messageRepository.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    //LIST INCOMING MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "read/myrecieved/{users}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users") Users users) {
        List<Message> messages = messageRepository.findAllByRecieverIs(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //LIST ALL SENT MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "read/mysent/{users}")
    public ResponseEntity<List<Message>> readSentMessages(@PathVariable("users") Users user) {
        List<Message> messages = messageRepository.findAllBySenderIs(user);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //READ MESSAGE CONTENT
    @RequestMapping(method = RequestMethod.GET, value = "/read/mymessage/{message}")
    @ResponseBody
    public String readMessages(@PathVariable("message") Message message) {
       return (message.getContent());
    }


    //CALCULATE EXPECTED FOR THE DAY
    //CALCULATE EXPECTED FOR THE WEEK


    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<Void> deleteAllMessages() {
        messageRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showallmessages")
    public ResponseEntity<List<Message>> showAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
