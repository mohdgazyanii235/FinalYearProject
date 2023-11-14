package com.fyp.erpapi.erpapi.configuration;

import com.fyp.erpapi.erpapi.service.CustomOIDCUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Log4j2
public class OAuth2LoginSecurityConfig {

    @Autowired
    private CustomOIDCUserService customOIDCUserService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/*"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this.userAuthoritiesMapper())
                                .oidcUserService(customOIDCUserService)))

                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority.getAuthority().equals("OIDC_USER") || authority.getAuthority().equals("OAUTH_USER")) {
                    mappedAuthorities.add(() -> "ROLE_USER");
                }
            });
            return mappedAuthorities;
        };
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("Login success: " + event.getAuthentication());
        };
    }
}
