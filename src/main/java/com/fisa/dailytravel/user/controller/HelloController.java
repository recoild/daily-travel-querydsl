package com.fisa.dailytravel.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> getMethodName(JwtAuthenticationToken principal) {
        Map<String, Object> info = new HashMap<>();
        Map<String, Object> infoList = principal.getTokenAttributes();
        System.out.println(infoList.get("name"));

        info.put("name", principal.getName());
        info.put("tokenAttributes", principal.getTokenAttributes());
//        log.info("info: {}", info);
//        log.info("infoDetail:" + info.get("tokenAttributes"));
        Object tokenAttributes = info.get("tokenAttributes");

        return ResponseEntity.ok("Hello, " + principal.getName());
    }
}
