package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;


/**
 * REST CONTROLLER FOR MESSEGE SERVICES
 * Please consult the MessageServiceImpl class in the service package to see the detailed documentation
 * of the methods
 */
@RestController
@RequestMapping("/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        messageService.sendSingleMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/sendmsgs")
    public ResponseEntity<List<Message>> sendMultipleMessages(@RequestBody List<Message> message) {
        messageService.sendMultipleMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET, value = "read/myrecieved/{users}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users") String users) {
        List<Message> messages = messageService.getAllMessages(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "read/mysent/{users}")
    public ResponseEntity<List<Message>> readSentMessages(@PathVariable("users") String users) {
        List<Message> messages = messageService.readSentMessages(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/read/mymessage/{message}")
    @ResponseBody
    public ResponseEntity<String> readMessages(@PathVariable("message") Message message) {
        String msgContent = messageService.readMessages(message);
        return new ResponseEntity<>(msgContent, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/estimateday")
    @ResponseBody
    public String estimateDayMessages() {
        return messageService.estimateDayMessages();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/estimateweek")
    @ResponseBody
    public String estimateWeekMessages() {
        return messageService.estimateWeekMessages();
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteallmessages")
    public ResponseEntity<Void> deleteAllMessages() {
        messageService.deleteAllMessages();
        return new ResponseEntity<>(HttpStatus.GONE);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/showallmessages")
    public ResponseEntity<List<Message>> showAllMessages() {
        List<Message> messages = messageService.showAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }




}
