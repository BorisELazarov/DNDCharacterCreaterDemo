package com.example.dndcharactercreatordemo.exceptions.customs;

public class NotSoftDeletedException extends RuntimeException{
    private final String message;

    public NotSoftDeletedException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
