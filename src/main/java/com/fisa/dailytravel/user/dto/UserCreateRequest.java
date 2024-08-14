package com.fisa.dailytravel.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String uuid;
    private String email;
    private String picture;
}
