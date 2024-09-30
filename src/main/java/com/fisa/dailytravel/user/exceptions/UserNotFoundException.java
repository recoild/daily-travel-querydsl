package com.fisa.dailytravel.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String uuid) {
        super("User with UUID " + uuid + " not found");
    }
}