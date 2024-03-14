package com.fyp.authorizationserver.service;

import com.fyp.authorizationserver.data.UserRegistrationDTO;
import com.fyp.authorizationserver.entity.User;
import com.fyp.authorizationserver.exception.AlreadyExistsException;
import com.fyp.authorizationserver.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isEnabled() && user.isAccountNonExpired() && user.isCredentialsNonExpired()) {
                return user;
            } else {
                throw new UsernameNotFoundException("User account not accessible at this time");
            }

        } else {
            throw new UsernameNotFoundException("User with name " + username + " doesn't exist in the authorization server");
        }
    }

    public void registerNewUser(UserRegistrationDTO userRegistrationDTO) throws AlreadyExistsException {

        if (this.userRepository.existsByUsername(userRegistrationDTO.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        } else {
            User newUser = new User();
            newUser.setUsername(userRegistrationDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
            this.userRepository.save(newUser);
        }
    }
}
