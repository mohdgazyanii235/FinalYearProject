package com.fyp.resourceserver.controller;

import com.fyp.resourceserver.service.UserInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing user information at the Resource Server.
 * <p>
 * This controller provides endpoints for accessing user information
 * secured by OAuth2 scopes and JWT-based authentication.
 * </p>
 */
@RestController
@AllArgsConstructor
public class UserInfoController {

    private final UserInformationService userInformationService;


    /**
     * Handles the request to get user information.
     * <p>
     * This endpoint requires the caller to have 'SCOPE_user.read' and 'SCOPE_openid'
     * authorities granted by an OAuth2 authorization server. It retrieves user information
     * based on the subject (sub) claim of the provided JWT.
     * </p>
     *
     * @param jwt The {@link Jwt} token representing the authenticated user's claims.
     * @return ResponseEntity containing the user's information if the request is authorized.
     */
    @GetMapping("/userinfo")
    @PreAuthorize("hasAuthority('SCOPE_user.read') && hasAuthority('SCOPE_openid')")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok().body(this.userInformationService.loadUserByEmail(jwt.getSubject()));
    }
}

