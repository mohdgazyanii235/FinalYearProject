package com.fyp.erpapi.erpapi.configs;

import com.fyp.erpapi.erpapi.entity.User;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Log4j2
public class WebSecurityConfig {

    

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(withDefaults())
                .oauth2Login(customizer -> customizer.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2LoginHandler()).oidcUserService(OIDCLoginHandler())))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .build();
    }


    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2LoginHandler() {
        return userRequest -> {
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            return User
                    .builder()
                    .id(oAuth2User.getAttribute("sub"))
                    .firstName(oAuth2User.getAttribute("given_name"))
                    .lastName(oAuth2User.getAttribute("family_name"))
                    .imageUrl(oAuth2User.getAttribute("picture"))
                    .email(oAuth2User.getAttribute("email"))
                    .attributes(oAuth2User.getAttributes())
                    .authorities(oAuth2User.getAuthorities())
                    .build();
        };
    }

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> OIDCLoginHandler() {
        return userRequest -> {
            OidcUserService delegate = new OidcUserService();
            OidcUser oidcUser = delegate.loadUser(userRequest);
            return User
                    .builder()
                    .id(oidcUser.getSubject())
                    .firstName(oidcUser.getGivenName())
                    .lastName(oidcUser.getFamilyName())
                    .imageUrl(oidcUser.getPicture())
                    .email(oidcUser.getEmail())
                    .attributes(oidcUser.getAttributes())
                    .authorities(oidcUser.getAuthorities())
                    .build();
        };
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("Login success: " + event.getAuthentication());
        };
    }

}
