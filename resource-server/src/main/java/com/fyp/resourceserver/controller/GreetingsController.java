package com.fyp.resourceserver.controller;

import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreetingsController {

    @GetMapping("/hello")
    Map<String, String> hello(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("message", "Hello "+ jwt.getSubject());
    }

}
