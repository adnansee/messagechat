package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MessageService messageService;

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

//READ ALL RECIEVED MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "read/myrecieved/{users}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users") String users) {
        List<Message> messages = messageService.getAllReceivedMessages(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

//READ ALL SENT MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "read/mysent/{users}")
    public ResponseEntity<List<Message>> readSentMessages(@PathVariable("users") String users) {
        List<Message> messages = messageService.getAllSentMessages(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

//READ MESSAGE CONTENT
    @RequestMapping(method = RequestMethod.GET, value = "/read/mymessage/{message}")
    @ResponseBody
    public ResponseEntity<String> readMessagesById(@PathVariable("message") String message_id) {
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
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteallmessages")
    public ResponseEntity<Void> deleteAllMessages() {
        messageService.deleteAllMessages();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

//SHOW ALL MESSAGES
    @RequestMapping(method = RequestMethod.GET, value = "/showallmessages")
    public ResponseEntity<List<Message>> showAllMessages() {
        List<Message> messages = messageService.showAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }


}
