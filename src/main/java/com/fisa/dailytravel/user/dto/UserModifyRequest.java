package com.fisa.dailytravel.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyRequest {
    private String uuid;
    private String nickname;
    private String profileImage;
}
