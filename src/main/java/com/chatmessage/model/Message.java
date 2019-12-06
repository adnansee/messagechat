package com.chatmessage.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



import java.time.LocalDateTime;

/** MESSAGE CLASS
 * Uses Lombok
 */

@Document
@Data
public class Message {

    @Id
    private String id;
    private String subject;
    private String content;
    private Users receiver;     //Give annotation '@NonNull' if send_id must be added to the message
    private Users sender;       //Give annotation '@NonNull' if the receiver_id must be added to the message
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime localDateTime;


}
