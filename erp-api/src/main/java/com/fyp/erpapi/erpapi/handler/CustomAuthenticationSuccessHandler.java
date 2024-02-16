package com.fyp.erpapi.erpapi.handler;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String oidcUserEmail = oidcUser.getAttribute("email");
        if (this.userService.isUserOnboardingComplete(oidcUserEmail)) {
            response.sendRedirect("http://localhost:3000/dashboard");
        } else {
            response.sendRedirect("http://localhost:3000/onboarding");
        }
    }
}
