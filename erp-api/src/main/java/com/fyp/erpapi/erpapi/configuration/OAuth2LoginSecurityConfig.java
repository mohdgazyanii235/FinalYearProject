package com.fyp.erpapi.erpapi.configuration;

import com.fyp.erpapi.erpapi.handler.CustomAuthenticationSuccessHandler;
import com.fyp.erpapi.erpapi.service.CustomOIDCUserService;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Log4j2
@AllArgsConstructor
@EnableMethodSecurity
public class OAuth2LoginSecurityConfig {

    private final CustomOIDCUserService customOIDCUserService;
    private final UserService userService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // TODO get into CSRF security later
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(this.myCorsConfiguration()))
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/login/oauth2/code/auth-server"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOIDCUserService))
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

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("Login success: " + event.getAuthentication());
        };
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
}
