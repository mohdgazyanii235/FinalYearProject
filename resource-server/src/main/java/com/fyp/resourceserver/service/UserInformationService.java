package com.fyp.resourceserver.service;

import com.fyp.resourceserver.data.UserInformationDTO;
import com.fyp.resourceserver.entity.UserInformation;
import com.fyp.resourceserver.repository.UserInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for managing user information.
 * <p>
 * This service provides business logic to interact with the {@link UserInformationRepository}
 * for operations related to user information, such as retrieving user details by email.
 * </p>
 */
@Service
@AllArgsConstructor
public class UserInformationService {

    private final UserInformationRepository userInformationRepository;


    /**
     * Loads a user's information by their email address and converts it to a {@link UserInformationDTO}.
     * <p>
     * This method fetches user information from the database using the provided email. It then maps
     * the entity data to a {@link UserInformationDTO} for use in higher layers of the application, especially
     * for API responses. If no user information is found for the given email, a {@link UsernameNotFoundException}
     * is thrown.
     * </p>
     *
     * @param email The email address of the user whose information is being requested.
     * @return A {@link UserInformationDTO} containing the user's information.
     * @throws UsernameNotFoundException If no user information exists for the provided email.
     */
    public UserInformationDTO loadUserByEmail(String email) {
        Optional<UserInformation> userInformationOptional = this.userInformationRepository.getUserInformationByEmail(email);

        if (userInformationOptional.isPresent()) {
            UserInformation userInformation = userInformationOptional.get();
            UserInformationDTO userInformationDTO = new UserInformationDTO();
            userInformationDTO.setEmail(userInformation.getEmail());
            userInformationDTO.setFirstName(userInformation.getFirstName());
            userInformationDTO.setLastName(userInformation.getLastName());
            userInformationDTO.setProfilePicture(userInformation.getProfilePicture());
            return userInformationDTO;
        } else {
            throw new UsernameNotFoundException("User with email " + email + " doesn't exist in the authorization server");
        }
    }

}
