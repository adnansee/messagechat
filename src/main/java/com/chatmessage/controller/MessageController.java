package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    //SEND MESSAGES
    @RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setLocalDateTime(LocalDateTime.now());
        messageRepository.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    //SEND MESSAGES MANY
    @RequestMapping(method = RequestMethod.POST, value = "/sendmsgs")
    public ResponseEntity<List<Message>> sendMultipleMessage(@RequestBody List<Message> messages) {
        for (Message message : messages) {
            message.setLocalDateTime(LocalDateTime.now());
        }
        messageRepository.saveAll(messages);
        return new ResponseEntity<>(messages, HttpStatus.CREATED);
    }


    //LIST INCOMING MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "read/myrecieved/{users}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users") Users users) {
        List<Message> messages = messageRepository.findAllByReceiverIs(users);
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
    //get current time and get message count since 00:00. Average it till the next 00:00
    @RequestMapping(method = RequestMethod.GET, value = "/read/estimateday")
    @ResponseBody
    public String estimateDayMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmsgs = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmsgs) / (now.toSecondOfDay())) * 86400;
        return ("Expected messages till the end of the day: " + estimatedMessagesInDay);
    }

    //CALCULATE EXPECTED FOR THE WEEK
    //get current time and get message count since 00:00. Average it till 7 days since 00:00 today.
    @RequestMapping(method = RequestMethod.GET, value = "/read/estimateweek")
    @ResponseBody
    public String estimateWeekMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmsgs = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmsgs) / (now.toSecondOfDay())) * 604800;
        return ("Expected messages till the end of the day: " + estimatedMessagesInDay);
    }


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
