package com.fisa.dailytravel.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserUpdateResponse {
    private String nickname;
    private String profileImagePath;
}
