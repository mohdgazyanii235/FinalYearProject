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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = this.userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isEnabled() && user.isAccountNonExpired() && user.isCredentialsNonExpired()) {
                return user;
            } else {
                throw new UsernameNotFoundException("User account not accessible at this time");
            }

        } else {
            throw new UsernameNotFoundException("User with email " + email + " doesn't exist in the authorization server");
        }
    }

    public void registerNewUser(UserRegistrationDTO userRegistrationDTO) throws AlreadyExistsException {

        if (this.userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        } else {
            User newUser = new User();
            newUser.setEmail(userRegistrationDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
            this.userRepository.save(newUser);
        }
    }
}
