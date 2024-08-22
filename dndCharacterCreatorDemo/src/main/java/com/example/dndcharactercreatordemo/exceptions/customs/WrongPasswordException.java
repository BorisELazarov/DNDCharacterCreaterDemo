package com.example.dndcharactercreatordemo.exceptions.customs;

public class WrongPasswordException extends RuntimeException{
    private final String message;

    public WrongPasswordException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
