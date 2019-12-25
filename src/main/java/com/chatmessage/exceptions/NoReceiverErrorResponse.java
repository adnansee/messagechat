package com.chatmessage.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
public class NoReceiverErrorResponse{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timeStamp;
    private int status;
    private String error;
}
