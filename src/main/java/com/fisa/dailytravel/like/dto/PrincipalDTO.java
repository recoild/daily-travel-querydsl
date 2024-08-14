package com.fisa.dailytravel.like.dto;


import lombok.*;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * infoDetail:{at_hash=uoiS7Ahf1Jqu9Me1JqHT3A, sub=111969318487959339341, email_verified=true,
 * iss=https://accounts.google.com, given_name=영하,
 * picture=https://lh3.googleusercontent.com/a/ACg8ocITbuhJ_pg3L6has17MvaLkNzNK3L7CaVKAWsJKjyTGakAzWA=s96-c,
 * aud=[756324778211-bt04f318ugn8fb23jrdg2u495i5m18d8.apps.googleusercontent.com],
 * azp=756324778211-bt04f318ugn8fb23jrdg2u495i5m18d8.apps.googleusercontent.com,
 * name=최영하, exp=2024-08-13T06:41:23Z, family_name=최, iat=2024-08-13T05:41:23Z,
 * email=gymlet789@gmail.com}
 */
@ToString
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
