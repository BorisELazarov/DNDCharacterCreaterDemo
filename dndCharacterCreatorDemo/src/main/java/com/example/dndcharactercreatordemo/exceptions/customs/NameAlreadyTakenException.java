package com.example.dndcharactercreatordemo.exceptions.customs;

public class NameAlreadyTakenException extends RuntimeException {
    private final String message;

    public NameAlreadyTakenException(String message) {
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
