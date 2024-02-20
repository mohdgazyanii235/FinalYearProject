package com.fyp.erpapi.erpapi.handler;

import com.fyp.erpapi.erpapi.data.UserProfileDataDTO;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import com.fyp.erpapi.erpapi.service.UserService;
import com.fyp.erpapi.erpapi.util.DataConversionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

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
