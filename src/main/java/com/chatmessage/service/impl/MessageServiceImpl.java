package com.chatmessage.service.impl;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import com.chatmessage.repository.MessageRepository;
import com.chatmessage.repository.UserRepository;
import com.chatmessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;


/**
 * MESSAGE SERVICE IMPLEMENTATION CLASS
 */


@Service
public class MessageServiceImpl implements MessageService {

    static final int constantSecondsInDay = 86400;
    static final int constantSecondsInWeek = 604800;
    private static DecimalFormat df = new DecimalFormat("0.00");


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
     * <p>
     * Moreover, the methods can be rewritten if the receiver is to be chosen via name and not id.
     *
     * @param {Message}
     * @return response entity with sent message
     */

    @Override
    public Message sendSingleMessage(Message message) {
        userNameCheck(message);
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
            userNameCheck(message);
        }
        messageRepository.saveAll(messages);
        return messages;
    }

    private void userNameCheck(Message message) {
        List<Users> allUsers = userRepository.findAll();
        Users receiver = message.getReceiver();
        Users sender = message.getSender();

        List<Message> messages = messageRepository.findAll();


        if (receiver != null && receiver.getId() != null) {
            for (Users user : allUsers) {
                if (receiver.getId().equals(user.getId())) {
                    message.setReceiver(user);
                }
            }
        }
        if (receiver != null && receiver.getName() != null) {
            for (Users user : allUsers) {
                if (receiver.getName().equals(user.getName())) {
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
        if (sender != null && sender.getName() != null) {
            for (Users user : allUsers) {
                if (sender.getName().equals(user.getName())) {
                    message.setReceiver(user);
                }
            }
        }

        for (Message message1 : messages
        ) {
            if (message.getId().equals(message1.getId())) {
                message.setId(message.getId() + "+");
            }

        }
        message.setLocalDateTime(LocalDateTime.now());

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
    public List<Message> getAllReceivedMessages(String users) {
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
    public List<Message> getAllSentMessages(String users) {
        return messageRepository.findAllBySender_Id(users);
    }

    /**
     * GET PARTICULAR MESSAGE CONTENT
     * This method lets us read a particular message. Messages are identified by the message id and if
     * no message exits the method returns a string "Message does not exist"
     *
     * @param {String} message id
     * @return {String} message content
     */
    @Override
    public String readMyMessage(String message_id) {
        List<Message> messages = messageRepository.findAll();
        String answer = "Message does not exist";
        for (Message message1 : messages
        ) {
            if (message1.getId().equals(message_id)) {
                answer = messageRepository.findMessageById(message_id).getContent();
            }
        }
        return answer;
    }


    /**
     * ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE DAY
     * This method estimated the number of messages expected till the end of the day.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a day, this returning an estimated number of messages.
     *
     * @param
     * @return {Double} number of estimated messages in one day
     */
    @Override
    public Double estimateDayMessages() {
        return getAnEstimate(constantSecondsInDay);
    }

    /**
     * ESTIMATE THE NUMBER OF MESSAGES THAT WILL BE SENT BY THE END OF THE WEEK
     * This method estimated the number of messages expected till the end of the week.
     * The method gets a count of the message send since 00:00 (Midnight). It then divides the count with the number
     * of seconds passed since midnight. This gives a per second message rate which is multiplied by the total number
     * of seconds in a week, this returning an estimated number of messages for a week.
     *
     * @param
     * @return {Double} number of estimated messages in a week
     */

    public Double estimateWeekMessages() {
        return getAnEstimate(constantSecondsInWeek);
    }


    /**
     * SINGLE METHOD TO ESTIMATE MESSAGES
     * This method is used above in the method estimateWeekMessages and estimate day messages
     *
     * @param constantSeconds
     * @return
     */
    private Double getAnEstimate(int constantSeconds) {
        List<Message> messages = messageRepository.findAll();
        double totalMessages = messages.size();
        LocalTime now = LocalTime.now(ZoneId.systemDefault());
        double estimatedMessagesInWeek = ((totalMessages) / (now.toSecondOfDay())) * constantSeconds;
        return Double.valueOf(df.format(estimatedMessagesInWeek));
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






