package com.fisa.dailytravel.user.exceptions;

import com.fisa.dailytravel.user.dto.UserCreateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                UserCreateResponse.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage())
                        .build().toString());
    }

}
