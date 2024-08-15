package com.fisa.dailytravel.like.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PrincipalDTO {
    private String at_hash;
    private String sub;
    private boolean email_verified;
    private String iss;
    private String given_name;
    private String picture;
    private String aud;
    private String azp;
    private String name;
    private String exp;
    private String family_name;
    private String iat;
    private String email;
}
