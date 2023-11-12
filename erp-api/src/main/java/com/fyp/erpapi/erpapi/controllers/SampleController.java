package com.fyp.erpapi.erpapi.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class SampleController {

    @GetMapping
    public Map<String, Object> currentUser(OAuth2AuthenticationToken authentication) {
        System.out.println(authentication.getPrincipal().getAttributes());
        return authentication.getPrincipal().getAttributes();
    }
}
