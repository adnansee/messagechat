package com.chatmessage.exceptions;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
        super("Message does not exist!");
    }
}
