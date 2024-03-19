package com.fyp.erpapi.erpapi.util;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;


/**
 * A custom implementation of {@link OAuth2AccessTokenResponseClient} for debugging purposes.
 * This class wraps the {@link DefaultAuthorizationCodeTokenResponseClient} to intercept and
 * possibly log or manipulate the OAuth2 token response for an authorization code grant request.
 *
 * Primarily useful for debugging OAuth2 authentication flows by providing a hook into the
 * process of obtaining an access token from the authorization code provided by the OAuth2 provider.
 */
@AllArgsConstructor
public class CustomAuthorizationTokenResponseClientForDebugging implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final DefaultAuthorizationCodeTokenResponseClient defaultAuthorizationCodeTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

    /**
     * Retrieves the OAuth2 access token response for a given authorization code grant request.
     * Delegates to the {@link DefaultAuthorizationCodeTokenResponseClient} but can be
     * customized for additional logging or error handling.
     *
     * @param authorizationGrantRequest The authorization code grant request.
     * @return The OAuth2 access token response.
     */
    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        return this.defaultAuthorizationCodeTokenResponseClient.getTokenResponse(authorizationGrantRequest);
    }

}
