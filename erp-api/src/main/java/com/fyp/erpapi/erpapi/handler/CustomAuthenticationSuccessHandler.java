package com.fyp.erpapi.erpapi.handler;

import com.fyp.erpapi.erpapi.data.UserProfileDataDTO;
import com.fyp.erpapi.erpapi.util.DataConversionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * Custom handler for processing actions upon successful authentication using OIDC.
 * This includes actions like creating a user information cookie and redirecting
 * the user to a designated page after successful authentication.
 */
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    /**
     * Handles actions to be performed once authentication is successful.
     *
     * @param request The request associated with the authentication attempt.
     * @param response The response where the post-authentication actions are written.
     * @param authentication The authentication object containing the principal (user) details.
     * @throws IOException if an input or output exception occurs.
     * @throws ServletException if a servlet exception occurs.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String oidcUserEmail = oidcUser.getAttribute("email");

        UserProfileDataDTO userProfileDataDTO = new UserProfileDataDTO();

        userProfileDataDTO.setEmail(oidcUserEmail);
        System.out.println(DataConversionUtil.convertToBase64(userProfileDataDTO));
        Cookie userInfoCookie = new Cookie("inf", DataConversionUtil.convertToBase64(userProfileDataDTO));
        userInfoCookie.setPath("/");
        response.addCookie(userInfoCookie);
        response.sendRedirect("http://localhost:3000/");
    }
}
