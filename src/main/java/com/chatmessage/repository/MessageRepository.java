package com.chatmessage.repository;

import com.chatmessage.model.Message;
import com.chatmessage.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {


    //FIND ALL MESSAGES INCOMING
    public List<Message> findAllByReceiverIs(Users users);

    //FIND ALL MESSAGES SENT
    public List<Message> findAllBySenderIs(Users users);

    //READ MESSAGE


}
