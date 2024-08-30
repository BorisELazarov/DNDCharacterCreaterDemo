package com.example.dndcharactercreatordemo.exceptions.customs;

public class EmailAlreadyTakenException extends RuntimeException{
    private final String message;

    public EmailAlreadyTakenException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
