package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.config.CustomSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;

@CustomSpringBootTest
class UserControllerTest {
    private JwtAuthenticationToken jwtAuthenticationToken;

    @BeforeEach
    void setup() {
        jwtAuthenticationToken = Mockito.mock(JwtAuthenticationToken.class);
        Mockito.when(jwtAuthenticationToken.getName()).thenReturn("uuid");
        Mockito.when(jwtAuthenticationToken.getTokenAttributes()).thenReturn(Map.of(
                "email", "test@example.com",
                "picture", "test_picture"
        ));
    }

}
