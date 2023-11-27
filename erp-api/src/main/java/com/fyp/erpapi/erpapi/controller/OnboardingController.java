package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onboarding")
public class OnboardingController {

    /*
    Following endpoints needed:
    1) /onboarding/{userId}/userInfo (POST/PUT) giving users the ability to updated things like firstname, lastname, etc.
    2) /onboarding/{userId}/companyInfo (POST/PUT) giving users the ability to join company and set their position - some functionality of approval to be added here.
    3) /onboarding/{userId}/profileInfo (POST/PUT) giving users the ability to set their profile picture, etc.
     */

}
