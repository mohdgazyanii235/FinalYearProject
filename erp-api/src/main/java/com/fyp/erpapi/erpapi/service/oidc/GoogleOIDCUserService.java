package com.fyp.erpapi.erpapi.service.oidc;

import com.fyp.erpapi.erpapi.data.OIDCUserInformationDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
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

/**
 * Custom OIDC User Service for users authenticated through Google.
 * Extends the default OIDC user service to incorporate additional processing
 * like user registration and authority mapping specific to users authenticated by Google.
 */
@AllArgsConstructor
public class GoogleOIDCUserService extends OidcUserService {


    private final UserRepository userRepository;
    private final UserService userService;


    /**
     * Loads the OIDC user after authentication and performs custom processing like
     * checking if the user is already registered and creating a new user if not.
     *
     * @param userRequest The user request after successful OIDC authentication.
     * @return A {@link OidcUser} with authorities mapped from the user's roles.
     * @throws OAuth2AuthenticationException if an authentication exception occurs during processing.
     */
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);
        OIDCUserInformationDTO oidcUserInformationDTO = new OIDCUserInformationDTO(oidcUser.getAttributes());
        Optional<User> userOptional = userRepository.getRegisteredUserByEmailAndIssuer(oidcUserInformationDTO.getEmail(), SSOIssuer.GOOGLE);
        if (userOptional.isEmpty()) {
            try {
                return userService.registerUser(oidcUserInformationDTO, oidcUser.getIdToken(), oidcUser.getUserInfo(), SSOIssuer.GOOGLE);
            } catch (NoSuchRoleException | AlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        } else {
            User user = userOptional.get();
            List<GrantedAuthority> mappedAuthorities = (List<GrantedAuthority>) user.getAuthorities();
            return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        }
    }
}
