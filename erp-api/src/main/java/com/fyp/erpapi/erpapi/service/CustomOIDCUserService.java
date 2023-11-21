package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.GoogleUserInformation;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomOIDCUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        GoogleUserInformation googleUserInformation = new GoogleUserInformation(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.getUserByEmail(googleUserInformation.getEmail());
        if (userOptional.isEmpty()) {
            userService.registerUser(googleUserInformation);
        }
        return oidcUser;
    }
}
