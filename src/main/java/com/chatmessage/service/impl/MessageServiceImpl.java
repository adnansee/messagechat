package com.chatmessage.service.impl;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {

    static final int constantSecondsInDay = 86400;
    static final int constantSecondsInWeek = 604800;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    /**
     * SEND SINGLE MESSAGE
     * This method sends the message to the MongoDB. Saving the message here is considered as sent.
     * Before adding the list of objects the method sets the date/time stamp of the sent message
     * Moreover this method also ensures that is the name is not provided and only the id is provided then the
     * method searches though already added users and if a user with the same id exists the method sets the name of
     * the receiver from the already present user in the database. It does the same for the sender if only id is given.
     *
     * @param {Message}
     * @return response entity with sent message
     */

    @Override
    public Message sendSingleMessage(Message message) {
        List<Users> allUsers = userRepository.findAll();
        Users receiver = message.getReceiver();
        Users sender = message.getSender();

        if (receiver != null && receiver.getId() != null) {
            for (Users user : allUsers) {
                if (receiver.getId().equals(user.getId())) {
                    message.setReceiver(user);
                }
            }
        }
        if (sender != null && sender.getId() != null) {
            for (Users user : allUsers) {
                if (sender.getId().equals(user.getId())) {
                    message.setSender(user);
                }
            }
        }
        message.setLocalDateTime(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }


    /**
     * SEND MULTIPLE MESSAGES
     * This method takes a number of messages as a list and sends as Post to MongoDB. Saving the messages here are
     * considered as sendind the messages.
     * Before adding the list of objects the method sets the date/time stamp of the sent messgae
     *
     * @param {List<Message>}
     * @return response entity with sent messages
     */

    @Override
    public List<Message> sendMultipleMessage(List<Message> messages) {
        for (Message message : messages) {
            sendSingleMessage(message);
        }
        return messages;
    }


    /**
     * LIST INCOMING MESSAGES
     * This method lists all messages RECEIVED by a certain user. Users are identified by user id
     * Before adding the list of objects the method sets the date/time stamp of the sent messages
     *
     * @param {String} receiver id
     * @return response entity with received messages
     */
    @Override
    public List<Message> getAllMessages(String users) {
        return messageRepository.findAllByReceiver_Id(users);
    }

    /**
     * LIST ALL SENT MESSAGES
     * This method lists all messages SENT by a certain user. Users are identified by user id
     * Before adding the list of objects the method sets the date/time stamp of the sent message
     *
     * @param {String} sender id
     * @return response entity with sent messages
     */
    @Override
    public List<Message> readSentMessages(String users) {
        return messageRepository.findAllBySender_Id(users);
    }

    /**
     * GET PARTICULAR MESSAGE CONTENT
     * This method lets us read a particular message. Messages are identified by the message
     *
     * @param {Message} message id
     * @return {String} message content
     */
    @Override
    public String readMessages(Message message) {
        return (message.getContent());
    }

    /**
     * ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE DAY
     * This method estimated the number of messages expected till the end of the day.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a day, this returning an estimated number of messages.
     *
     * @param
     * @return {String} number of estimated messages in one day
     */
    @Override
    public String estimateDayMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmessages = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmessages) / (now.toSecondOfDay())) * constantSecondsInDay;
        return ("Expected messages till the end of the day: " + estimatedMessagesInDay);
    }

    /**
     * ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE WEEK
     * This method estimated the number of messages expected till the end of the week.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a week, this returning an estimated number of messages for a week.
     *
     * @param
     * @return {String} number of estimated messages in a week
     */

    public String estimateWeekMessages() {
        List<Message> messages = messageRepository.findAll();
        double totalmessages = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInDay = ((totalmessages) / (now.toSecondOfDay())) * constantSecondsInWeek;
        return ("Expected messages till the end of the week: " + estimatedMessagesInDay);
    }

    /**
     * DELETE ALL MESSAGES
     * This method deletes all messages in the MongoDB.
     *
     * @param
     * @return {Http status code}
     */

    public void deleteAllMessages() {
        messageRepository.deleteAll();
    }

    /**
     * SHOW ALL MESSAGES
     * This method shows all the messages in the MongoDB
     *
     * @param
     * @return {Http status code}
     */
    public List<Message> showAllMessages() {
        return messageRepository.findAll();
    }

}
