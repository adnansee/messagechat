package com.chatmessage.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/** USER CLASS
 * Uses Lombok
 */


@Document
@Data
public class Users {

    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;


}
