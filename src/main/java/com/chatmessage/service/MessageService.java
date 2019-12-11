package com.chatmessage.service;


import com.chatmessage.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message sendSingleMessage(Message message);

    List<Message> sendMultipleMessage(List<Message> messages);

    List<Message> getAllMessages (String user_id);

    List<Message> readSentMessages (String user_id);

    String readMessages (Message message);

    String estimateDayMessages();

    String estimateWeekMessages();

    void deleteAllMessages();

    List<Message> showAllMessages();

    String readMyMessage(String message_id);


}
