package com.example.geektrust.exception;

public class Houseful extends Exception{

    private String message;

    public Houseful(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
