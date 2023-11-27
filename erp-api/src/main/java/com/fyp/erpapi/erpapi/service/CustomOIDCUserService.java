package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.GoogleUserInformation;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
@AllArgsConstructor
public class CustomOIDCUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        System.out.println("Attributes: " + oidcUser.getAttributes());
        System.out.println("Claims: " + oidcUser.getClaims());
        System.out.println("UserInfo: " + oidcUser.getUserInfo());
        GoogleUserInformation googleUserInformation = new GoogleUserInformation(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.getUserByEmail(googleUserInformation.getEmail());
        if (userOptional.isEmpty()) {
            try {
                return userService.registerUser(googleUserInformation, oidcUser.getIdToken(), oidcUser.getUserInfo());
            } catch (NoSuchRoleException e) {
                throw new RuntimeException(e);
            }
        } else {
            User user = userOptional.get();
            List<GrantedAuthority> mappedAuthorities = (List<GrantedAuthority>) user.getAuthorities();
            return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        }
    }
}
