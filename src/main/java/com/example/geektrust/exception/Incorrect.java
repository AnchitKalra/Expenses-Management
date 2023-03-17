package com.example.geektrust.exception;

public class Incorrect extends Exception{

    private String message;

    public Incorrect(String message) {
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
