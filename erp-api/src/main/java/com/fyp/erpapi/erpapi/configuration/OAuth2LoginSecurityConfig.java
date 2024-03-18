package com.fyp.erpapi.erpapi.configuration;

import com.fyp.erpapi.erpapi.exception.UnknownRegistrationIdException;
import com.fyp.erpapi.erpapi.handler.CustomAuthenticationSuccessHandler;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import com.fyp.erpapi.erpapi.service.UserService;
//import com.fyp.erpapi.erpapi.service.oidc.AuthServerOIDCUserService;
import com.fyp.erpapi.erpapi.service.oidc.AuthServerOIDCUserService;
import com.fyp.erpapi.erpapi.service.oidc.GoogleOIDCUserService;
import com.fyp.erpapi.erpapi.util.CustomAuthorizationTokenResponseClientForDebugging;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Log4j2
@AllArgsConstructor
@EnableMethodSecurity
public class OAuth2LoginSecurityConfig {

    private final UserService userService;
    private final UserRepository userRepository;

    private static final String GOOGLE = "google";
    private static final String AUTH_SERVER = "auth-server";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // TODO get into CSRF security later
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(this.myCorsConfiguration()))
                .oauth2Login(oauth2 -> oauth2
                        .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig
                                .accessTokenResponseClient(new CustomAuthorizationTokenResponseClientForDebugging()))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/{registrationId}"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.getOIDCUserService()))
                        .successHandler(new CustomAuthenticationSuccessHandler(userService)))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.info("Access denied: " + accessDeniedException.getMessage());
                            response.sendRedirect("/access-denied");
                        }))
                .build();
    }


//    Below code essentially returns which oidc user service to use depending on which auth server the user chose
    private OAuth2UserService<OidcUserRequest, OidcUser> getOIDCUserService() {
        return (userRequest) -> {
            if (userRequest.getClientRegistration().getRegistrationId().equals(GOOGLE)) {
                return new GoogleOIDCUserService(userRepository, userService).loadUser(userRequest);
            } else if (userRequest.getClientRegistration().getRegistrationId().equals(AUTH_SERVER)) {
                return new AuthServerOIDCUserService(userRepository, userService, restTemplate()).loadUser(userRequest);
//                return new GoogleOIDCUserService(userRepository, userService).loadUser(userRequest);
            } else {
                throw new UnknownRegistrationIdException("Unknown Registration ID");
            }
        };
    }



    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> log.info("Login success: " + event.getAuthentication());
    }

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

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFireWall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        firewall.setAllowUrlEncodedPercent(true);
        return firewall;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
