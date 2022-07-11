package com.orderservice.controller;

import com.orderservice.domain.message.BaseMessage;
import com.orderservice.domain.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    public <T> ResponseEntity<?> successResponse(String message, T data) {
        Message<T> responseMessage = new Message<>(
                HttpStatus.OK.value() + "",
                true,
                message,
                data);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> successResponse(String message) {
        return successResponse(message, null);
    }

    public <T> ResponseEntity<?> successResponse(T data) {
        return successResponse(null, data);
    }

    public ResponseEntity<?> failedResponse(String code, String message) {
        String prefix = "[Order-Service]";
        BaseMessage responseMessage = new BaseMessage(code, false, String.format("%s %s", prefix, message));
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(Integer.parseInt(code)));
    }
}