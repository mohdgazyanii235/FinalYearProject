package com.fyp.resourceserver.controller;

import com.fyp.resourceserver.service.UserInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserInfoController {

    private final UserInformationService userInformationService;

    @GetMapping("/userinfo")
    @PreAuthorize("hasAuthority('SCOPE_user.read') && hasAuthority('SCOPE_openid')")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok().body(this.userInformationService.loadUserByEmail(jwt.getSubject()));
    }
}

