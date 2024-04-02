package com.fyp.erpapi.erpapi.configuration;

import com.fyp.erpapi.erpapi.exception.UnknownRegistrationIdException;
import com.fyp.erpapi.erpapi.handler.CustomAuthenticationSuccessHandler;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import com.fyp.erpapi.erpapi.service.UserService;
import com.fyp.erpapi.erpapi.service.oidc.AuthServerOIDCUserService;
import com.fyp.erpapi.erpapi.service.oidc.GoogleOIDCUserService;
import com.fyp.erpapi.erpapi.util.CustomAuthorizationTokenResponseClientForDebugging;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


/**
 * Configuration for OAuth2 login security, enabling method security, and setting up beans required for OAuth2 authentication.
 * This configuration class does the following:
 * - integrates custom user services for OIDC.
 * - Sets up CORS configuration.
 * - Establishes a custom success handler for authentication events.
 */
@Configuration
@Log4j2
@AllArgsConstructor
@EnableMethodSecurity
public class OAuth2LoginSecurityConfig {

    private final UserService userService;
    private final UserRepository userRepository;

    private static final String GOOGLE = "google";
    private static final String AUTH_SERVER = "auth-server";


     /**
     * Configures the HttpSecurity to use OAuth2 login, enable CORS from a specific source, and customize authentication success and exception handling.
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // TODO get into CSRF security later
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(this.myCorsConfiguration()))
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig
                                .accessTokenResponseClient(new CustomAuthorizationTokenResponseClientForDebugging()))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/{registrationId}"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.getOIDCUserService()))
                        .successHandler(new CustomAuthenticationSuccessHandler()))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.info("Access denied: " + accessDeniedException.getMessage());
                            response.sendRedirect("/access-denied");
                        }))
                .build();
    }


    /**
     * Provides an OAuth2UserService based on the OIDC user request that determines the appropriate service to use based on the client registration ID.
     * @return an instance of OAuth2UserService for OIDC user requests
     */
    private OAuth2UserService<OidcUserRequest, OidcUser> getOIDCUserService() {
        return (userRequest) -> {
            if (userRequest.getClientRegistration().getRegistrationId().equals(GOOGLE)) {
                return new GoogleOIDCUserService(userRepository, userService).loadUser(userRequest);
            } else if (userRequest.getClientRegistration().getRegistrationId().equals(AUTH_SERVER)) {
                return new AuthServerOIDCUserService(userRepository, userService, restTemplate()).loadUser(userRequest);
            } else {
                throw new UnknownRegistrationIdException("Unknown Registration ID");
            }
        };
    }

    /**
     * Creates an application listener that logs successful authentication events.
     * @return the ApplicationListener for authentication success events
     */
    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> log.info("Login success: " + event.getAuthentication());
    }


    /**
     * Configures CORS policy to allow requests from specific origins.
     * @return the CorsConfigurationSource with the configured CORS policy
     */
    @Bean
    CorsConfigurationSource myCorsConfiguration() {
        // TODO: Add this to report (Why am I doing this?)
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    /**
     * Configures the HttpFirewall to allow URL encoded slashes and percent signs.
     * This is done because some Authorization code exchange request urls have double slashes that spring security flags as malicious.
     * @return the configured HttpFirewall instance
     */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFireWall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        firewall.setAllowUrlEncodedPercent(true);
        return firewall;
    }

    /**
     * Provides a RestTemplate bean for making REST calls.
     * @return a new instance of RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


     static final class CsrfCookieFilter extends OncePerRequestFilter {

         @Override
         protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                 throws ServletException, IOException {
             // Retrieve CSRF token from "_csrf" attribute
             CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

             if (csrfToken != null) {
                 // Get the CSRF token from the cookies
                 String csrfTokenValue = null;
                 Cookie[] cookies = request.getCookies();
                 if (cookies != null) {
                     for (Cookie cookie : cookies) {
                         if ("XSRF-TOKEN".equals(cookie.getName())) {
                             csrfTokenValue = cookie.getValue();
                         }
                     }
                 }

                 // Optionally, you can validate the token or just ensure it's attached to the request
                 // For demonstration, we're just logging the token value
                 System.out.println("CSRF token from cookies: " + csrfTokenValue);
             }

             filterChain.doFilter(request, response);
         }


     }

}
