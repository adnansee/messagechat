package com.chatmessage.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;



@Document
@Data
public class Message {

    @Id
    private String id;
    private String subject;
    private String content;
    private Users receiver;
    private Users sender;
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime localDateTime;


}
