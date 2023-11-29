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
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public UserDetails loadUserById(Long id) {
        Optional<User> user = this.userRepository.getUserById(id);
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
        user.addRole(this.roleService.getRoleByName("NON_ONBOARDED_USER_A"));
        this.userRepository.save(user);
        return new DefaultOidcUser(user.getAuthorities(), idToken, userInfo);
    }

    public void assignRolesToUser(UserRoleDTO userRoleDTO) {
        Optional<User> userOptional = this.userRepository.getUserByEmail(userRoleDTO.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRoleDTO.getRoleNames().forEach(roleName -> {
                try {
                    user.addRole(this.roleService.getRoleByName(roleName));
                } catch (NoSuchRoleException e) {
                    throw new RuntimeException(e);
                }
            });
            this.userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(userRoleDTO.getEmail());
        }
    }

    public String getFirstName(Long id) {
        Optional<String> firstNameOptional = this.userRepository.getFirstNameById(id);
        if (firstNameOptional.isPresent()) {
            return firstNameOptional.get();
        } else {
            throw new UsernameNotFoundException(id.toString());
        }
    }

    public String getLastName(Long id) {
        Optional<String> lastNameOptional = this.userRepository.getLastNameById(id);
        if (lastNameOptional.isPresent()) {
            return lastNameOptional.get();
        } else {
            throw new UsernameNotFoundException(id.toString());
        }
    }

    public void updateFirstName(Long id, String firstName) {
        this.userRepository.updateFirstName(id, firstName);
    }

    public void updateRole(Long id, String roleName) {
        User user = (User) this.loadUserById(id);
        Set<Role> roles = user.getRoles();
        try {
            Role onBoardedUserA = this.roleService.getRoleByName("NON_ONBOARDED_USER_A");
            Role onBoardedUserB = this.roleService.getRoleByName("NON_ONBOARDED_USER_B");
            Role onBoardedUserC = this.roleService.getRoleByName("NON_ONBOARDED_USER_C");
            if (roleName.equals("NON_ONBOARDED_USER_B") && roles.contains(onBoardedUserA)) {
                user.getRoles().remove(onBoardedUserA);
                user.addRole(onBoardedUserB);
            } else if (roleName.equals("NON_ONBOARDED_USER_C") && roles.contains(onBoardedUserB)) {
                user.getRoles().remove(onBoardedUserB);
                user.addRole(onBoardedUserC);
            } else if (roleName.equals("USER") && roles.contains(onBoardedUserC)) {
                Role userRole = this.roleService.getRoleByName("USER");
                user.getRoles().remove(onBoardedUserC);
                user.addRole(userRole);
            }
        } catch (NoSuchRoleException e) {
            System.out.println(e.getMessage());
        }
    }
}
