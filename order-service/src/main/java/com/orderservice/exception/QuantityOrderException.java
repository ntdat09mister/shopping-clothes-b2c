package com.orderservice.exception;

public class QuantityOrderException extends RuntimeException{
    public QuantityOrderException(String message){
        super(message);
    }
}
