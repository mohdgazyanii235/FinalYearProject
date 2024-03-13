package com.fyp.resourceserver.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GreetingsService {


    @PreAuthorize("hasAuthority('SCOPE_user.read')")
    public Map<String, String> greet() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Map.of("message", "Hello " + jwt.getSubject());
    }
}
