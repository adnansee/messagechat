package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST CONTROLLER FOR MESSAGE SERVICES
 * Please consult the MessageServiceImpl class in the service package to see the detailed documentation
 * of the methods
 */

@RestController
@RequestMapping("/messages")
@CrossOrigin("*")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //SEND SINGLE MESSAGE
    @RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        messageService.sendSingleMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //SEND MULTIPLE MESSAGES
    @RequestMapping(method = RequestMethod.POST, value = "/sendmsgs")
    public ResponseEntity<List<Message>> sendMultipleMessages(@RequestBody List<Message> message) {
        messageService.sendMultipleMessage(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    //READ ALL RECEIVED MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "/recieved/{users_id}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users_id") String users_id) {
        List<Message> messages = messageService.getAllReceivedMessages(users_id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //READ ALL SENT MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "/sent/{users_id}")
    public ResponseEntity<List<Message>> readSentMessages(@PathVariable("users_id") String users_id) {
        List<Message> messages = messageService.getAllSentMessages(users_id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //READ MESSAGE CONTENT
    @RequestMapping(method = RequestMethod.GET, value = "/read/{message_id}")
    @ResponseBody
    public ResponseEntity<String> readMessagesById(@PathVariable("message_id") String message_id) {
        String msgContent = messageService.readMyMessage(message_id);
        return new ResponseEntity<>(msgContent, HttpStatus.OK);
    }

    //ESTIMATE THE NUMBER OF MESSAGES IN A DAY
    @RequestMapping(method = RequestMethod.GET, value = "/estimateday")
    @ResponseBody
    public Double estimateDayMessages() {
        return messageService.estimateDayMessages();
    }

    //ESTIMATE THE NUMBER OF MESSAGES IN A WEEK
    @RequestMapping(method = RequestMethod.GET, value = "/estimateweek")
    @ResponseBody
    public Double estimateWeekMessages() {
        return messageService.estimateWeekMessages();
    }

    //DELETE ALL MESSAGES
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<Void> deleteAllMessages() {
        messageService.deleteAllMessages();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    //SHOW ALL MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "/getall")
    public ResponseEntity<List<Message>> showAllMessages() {
        List<Message> messages = messageService.showAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
