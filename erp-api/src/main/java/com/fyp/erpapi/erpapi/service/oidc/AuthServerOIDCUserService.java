package com.fyp.erpapi.erpapi.service.oidc;

import com.fyp.erpapi.erpapi.data.GoogleUserInformation;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthServerOIDCUserService extends OidcUserService {


    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);
        return oidcUser;
    }
}
