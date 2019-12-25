package com.chatmessage.exceptions;

public class ReceiverNotFoundException extends RuntimeException {

    public ReceiverNotFoundException (){
        super("Receiver not named");
    }
}
