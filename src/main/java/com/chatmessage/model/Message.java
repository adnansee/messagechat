package com.chatmessage.model;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotEmpty;
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
    private Users receiver;
    private Users sender;
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime localDateTime;


}
