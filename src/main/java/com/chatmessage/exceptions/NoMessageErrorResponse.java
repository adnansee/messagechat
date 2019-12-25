package com.chatmessage.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoMessageErrorResponse {


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timeStamp;
    private int status;
    private String error;
}

