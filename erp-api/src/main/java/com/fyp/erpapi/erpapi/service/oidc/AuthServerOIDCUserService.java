package com.fyp.erpapi.erpapi.service.oidc;

import com.fyp.erpapi.erpapi.data.OIDCUserInformationDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Custom OIDC User Service for users authenticated through a custom Auth Server.
 * This service extends the default OIDC user service to handle OIDC authenticated users,
 * specifically those authenticated via an internal or custom authentication server.
 */
@AllArgsConstructor
public class AuthServerOIDCUserService extends OidcUserService {


    private final UserRepository userRepository;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private static final String USER_INFO_ENDPOINT = "http://localhost:8082/userinfo";


    /**
     * Retrieves user details from a custom user information endpoint.
     *
     * @param accessToken The access token to authenticate the request to the user info endpoint.
     * @return {@link OidcUserInfo} containing details of the authenticated user.
     * @throws UsernameNotFoundException if user details cannot be retrieved from the resource server.
     */
    private OidcUserInfo getUserDetails(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(USER_INFO_ENDPOINT, HttpMethod.GET, entity, Object.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            HashMap<String, Object> userDetails = (HashMap<String, Object>) response.getBody();
            Assert.notNull(userDetails, "Resource Server Responded With Empty Body.");
            String email = (String) userDetails.get("email");
            String firstName = (String) userDetails.get("firstName");
            String lastName = (String) userDetails.get("lastName");
            String profilePicture = (String) userDetails.get("profilePicture");
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", email);
            claims.put("email", email);
            claims.put("name", firstName + " " + lastName);
            claims.put("given_name", firstName);
            claims.put("family_name", lastName);
            claims.put("picture", profilePicture);
            return new OidcUserInfo(claims);
        } else {
            throw new UsernameNotFoundException("User not found on resource server.");
        }
    }

    /**
     * Loads the OIDC user after authentication and performs custom processing like
     * checking if the user is already registered and creating a new user if not.
     *
     * @param userRequest the user request
     * @return {@link OidcUser} containing the authenticated user.
     */

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        Optional<User> userOptional = userRepository.getRegisteredUserByEmailAndIssuer((String) userRequest.getIdToken().getClaims().get("sub"), SSOIssuer.AUTH_SERVER);
        OidcUserInfo oidcUserInfo = getUserDetails(userRequest.getAccessToken().getTokenValue());
        if (userOptional.isEmpty()) {
            OIDCUserInformationDTO oidcUserInformationDTO = new OIDCUserInformationDTO(oidcUserInfo.getClaims());
            try {
                return userService.registerUser(oidcUserInformationDTO, userRequest.getIdToken(), oidcUserInfo, SSOIssuer.AUTH_SERVER);
            } catch (NoSuchRoleException | AlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        } else {
            User user = userOptional.get();
            List<GrantedAuthority> mappedAuthorities = (List<GrantedAuthority>) user.getAuthorities();

            return new DefaultOidcUser(mappedAuthorities, userRequest.getIdToken(), oidcUserInfo);
        }
    }
}
