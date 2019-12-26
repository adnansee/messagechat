package com.chatmessage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


   @ExceptionHandler(ReceiverNotFoundException.class)
    public ResponseEntity<NoReceiverErrorResponse> receiverNotFound(Exception ex) {
        NoReceiverErrorResponse errorResponse = new NoReceiverErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<NoMessageErrorResponse> messageNotFound(Exception ex) {
        NoMessageErrorResponse errorResponse = new NoMessageErrorResponse();
        errorResponse.setTimeStamp(LocalDateTime.now());
        errorResponse.setError(ex.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // TODO
    // error handle for @Valid

    }


