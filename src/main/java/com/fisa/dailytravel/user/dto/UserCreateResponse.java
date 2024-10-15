package com.fisa.dailytravel.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class UserCreateResponse {
    private HttpStatus status;
    private String message;
}
