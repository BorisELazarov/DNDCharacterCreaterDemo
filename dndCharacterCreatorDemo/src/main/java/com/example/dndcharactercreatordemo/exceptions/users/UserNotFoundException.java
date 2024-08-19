package com.example.dndcharactercreatordemo.exceptions.users;

public class UserNotFoundException extends RuntimeException{
    private final String message="User is not found!";

    @Override
    public String getMessage() {
        return message;
    }
}
