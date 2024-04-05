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


/**
 * Custom implementation of {@link UserDetailsService} for user authentication and registration services.
 * <p>
 * This service class provides mechanisms for loading user-specific data and registering new users.
 * It uses {@link UserRepository} to interact with the database and {@link PasswordEncoder} for password encoding.
 * </p>
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Loads the user's details given the username (email in this case).
     *
     * @param email The email of the user to load.
     * @return UserDetails of the requested user if found and account is in good standing.
     * @throws UsernameNotFoundException If no user is found with the given email or if the account is not in a good standing.
     */
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


    /**
     * Registers a new user with the details provided in {@link UserRegistrationDTO}.
     *
     * @param userRegistrationDTO The user registration data transfer object containing the email and password.
     * @throws AlreadyExistsException If a user with the given email already exists.
     */
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
