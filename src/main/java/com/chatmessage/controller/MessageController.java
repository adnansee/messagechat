package com.chatmessage.controller;

import com.chatmessage.model.Message;
import com.chatmessage.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.List;


/**
 *      PLEASE WORK IT SEEMS TO PLEASE JUST WORK
 *
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    static final int constantSecondsInDay = 86400;
    static final int constantSecondsInWeek = 604800;

    @Autowired
    private MessageRepository messageRepository;


    /** SEND SINGLE MESSAGE
     * This method sends the message to the MongoDB. Saving the message here is considered as sent.
     * Before adding the list of objects the method sets the date/time stamp of the sent messgae
     * @param {Message}
     * @return response entity with sent message
     */

    @RequestMapping(method = RequestMethod.POST, value = "/sendmsg")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setLocalDateTime(LocalDateTime.now());
        messageRepository.save(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /** SEND MULTIPLE MESSAGES
     * This method takes a number of messages as a list and sends as Post to MongoDB. Saving the messages here are
     * considered as sendind the messages.
     * Before adding the list of objects the method sets the date/time stamp of the sent messgae
     * @param  {List<Message>}
     * @return response entity with sent messages
     *
     */

    @RequestMapping(method = RequestMethod.POST, value = "/sendmsgs")
    public ResponseEntity<List<Message>> sendMultipleMessage(@RequestBody List<Message> messages) {
        for (Message message : messages) {
            message.setLocalDateTime(LocalDateTime.now());
        }
        messageRepository.saveAll(messages);
        return new ResponseEntity<>(messages, HttpStatus.CREATED);
    }

    /** LIST INCOMING MESSAGES
     * This method lists all messages RECEIVED by a certain user. Users are identified by user id
     * Before adding the list of objects the method sets the date/time stamp of the sent messages
     * @param  {String} receiver id
     * @return response entity with received messages
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "read/myrecieved/{users}")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable("users") String users) {
        List<Message> messages =  messageRepository.findAllByReceiver_Id(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /** LIST ALL SENT MESSAGES
     * This method lists all messages SENT by a certain user. Users are identified by user id
     * Before adding the list of objects the method sets the date/time stamp of the sent message
     * @param  {String} sender id
     * @return response entity with sent messages
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "read/mysent/{users}")
    public ResponseEntity<List<Message>> readSentMessages(@PathVariable("users") String users) {
        List<Message> messages = messageRepository.findAllBySender_Id(users);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /** GET PARTICULAR MESSAGE CONTENT
     * This method lets us read a particular message. Messages are identified by the message
     * @param  {Message} message id
     * @return {String} message content
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "/read/mymessage/{message}")
    @ResponseBody
    public String readMessages(@PathVariable("message") Message message) {
        return (message.getContent());
    }

    /** ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE DAY
     * This method estimated the number of messages expected till the end of the day.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a day, this returning an estimated number of messages.
     * @param
     * @return {String} number of estimated messages in one day
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "/read/estimateday")
    @ResponseBody
    public String estimateDayMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmsgs = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmsgs) / (now.toSecondOfDay())) * constantSecondsInDay;
        return ("Expected messages till the end of the day: " + estimatedMessagesInDay);
    }

    /** ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE WEEK
     * This method estimated the number of messages expected till the end of the week.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a week, this returning an estimated number of messages for a week.
     * @param
     * @return {String} number of estimated messages in a week
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "/read/estimateweek")
    @ResponseBody
    public String estimateWeekMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmsgs = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmsgs) / (now.toSecondOfDay())) * constantSecondsInWeek;
        return ("Expected messages till the end of the week: " + estimatedMessagesInDay);
    }

    /** DELETE ALL MESSAGES
     * This method deletes all messages in the MongoDB.
     * @param
     * @return {Http status code}
     *
     */

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteall")
    public ResponseEntity<Void> deleteAllMessages() {
        messageRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.GONE);
    }

    /** SHOW ALL MESSAGES
     * This method shows all the messages in the MongoDB
     * @param
     * @return {Http status code}
     *
     */

    @RequestMapping(method = RequestMethod.GET, value = "/showallmessages")
    public ResponseEntity<List<Message>> showAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }




}
