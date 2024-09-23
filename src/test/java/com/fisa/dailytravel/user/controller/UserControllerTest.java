package com.fisa.dailytravel.user.controller;

import com.fisa.dailytravel.config.CustomSpringBootTest;
import com.fisa.dailytravel.user.dto.UserCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@CustomSpringBootTest
class UserControllerTest {
    private JwtAuthenticationToken jwtAuthenticationToken;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Create a mocked JwtAuthenticationToken with authorities
        jwtAuthenticationToken = new JwtAuthenticationToken(
                Mockito.mock(org.springframework.security.oauth2.jwt.Jwt.class),
                Set.of(new SimpleGrantedAuthority("ROLE_USER")), // Add role here
                "uuid"
        );

        Mockito.when(jwtAuthenticationToken.getTokenAttributes()).thenReturn(Map.of(
                "email", "test@example.com",
                "picture", "test_picture"
        ));

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }

    @Test
    void testSignin() throws Exception {
        UserCreateResponse userCreateResponse = new UserCreateResponse("User created successfully");

        mockMvc.perform(post("/v1/user")
                        .principal(jwtAuthenticationToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }
}
