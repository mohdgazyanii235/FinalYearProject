package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.GoogleUserInformation;
import com.fyp.erpapi.erpapi.data.UserRoleDTO;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.RoleRepository;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public boolean isUserOnboardingComplete(String oidcUserEmail) {
        return this.userRepository.isOnboardingCompleteByEmail(oidcUserEmail);
    }

    public OidcUser registerUser(GoogleUserInformation googleUserInformation, OidcIdToken idToken, OidcUserInfo userInfo) throws NoSuchRoleException {
        System.out.println(googleUserInformation.getClaims());
        User user = new User();
        user.setEmail(googleUserInformation.getEmail());
        user.setFirstName(googleUserInformation.getFirstName());
        user.setLastName(googleUserInformation.getLastName());
        user.setPassword(UUID.randomUUID().toString());
        user.setImageUrl(googleUserInformation.getImageUrl());
        Optional<Role> roleOptional = this.roleRepository.findByName("USER");
        if (roleOptional.isEmpty()) {
            throw new NoSuchRoleException("USER");
        } else {
            Role role = roleOptional.get();
            user.addRole(role);
        }
        this.userRepository.save(user);
        return new DefaultOidcUser(user.getAuthorities(), idToken, userInfo);
    }

    public void assignRolesToUser(UserRoleDTO userRoleDTO) {
        Optional<User> userOptional = this.userRepository.getUserByEmail(userRoleDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRoleDTO.getRoleNames().forEach(roleName -> {
                Optional<Role> role = this.roleRepository.findByName(roleName);
                role.ifPresent(user::addRole);
            });
            this.userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(userRoleDTO.getEmail());
        }
    }
}
