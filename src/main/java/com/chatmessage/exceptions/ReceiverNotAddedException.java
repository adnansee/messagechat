package com.chatmessage.exceptions;

public class ReceiverNotAddedException extends RuntimeException {

    public ReceiverNotAddedException(){
        super("Receiver not named");
    }
}
