package com.chatmessage.repository;

import com.chatmessage.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/** MESSAGE REPOSITORY CLASS WITH MONGOREPOSITORY EXTENDED
 *
 */
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {



    //INTERFACE METHOD TO GET ALL INCOMING MESSAGES VIA USER ID
    List<Message> findAllByReceiver_Id(String users);

    //INTERFACE METHOD TO GET ALL SENT MESSAGES VIA USER ID
    List<Message> findAllBySender_Id(String users);





}
