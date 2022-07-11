package com.orderservice.exception;

public class CantCancelOrderException extends RuntimeException{
    public CantCancelOrderException(String message){
        super(message);
    }
}
