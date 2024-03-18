package com.fyp.erpapi.erpapi.service.oidc;

import com.fyp.erpapi.erpapi.data.OIDCUserInformationDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
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
public class GoogleOIDCUserService extends OidcUserService {


    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);
        OIDCUserInformationDTO OIDCUserInformationDTO = new OIDCUserInformationDTO(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.getRegisteredUserByEmailAndIssuer(OIDCUserInformationDTO.getEmail(), SSOIssuer.GOOGLE);
        if (userOptional.isEmpty()) {
            try {
                return userService.registerUser(OIDCUserInformationDTO, oidcUser.getIdToken(), oidcUser.getUserInfo(), SSOIssuer.GOOGLE);
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
