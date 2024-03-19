package com.fyp.erpapi.erpapi.enumeration;

/**
 * Enumeration for Single Sign-On (SSO) issuers.
 * Defines the list of supported SSO providers for the application.
 * This enumeration is used to identify the source of authentication
 * for users logging in through different Authorization Servers.
 */
public enum SSOIssuer {


    /**
     * Google as the SSO provider.
     */
    GOOGLE,

    /**
     * Custom authentication server as the SSO provider.
     */
    AUTH_SERVER,

    /**
     * GitHub as the SSO provider.
     */
    GITHUB

}
