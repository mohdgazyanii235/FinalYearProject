package com.fyp.resourceserver.service;

import com.fyp.resourceserver.data.UserInformationDTO;
import com.fyp.resourceserver.entity.UserInformation;
import com.fyp.resourceserver.repository.UserInformationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInformationService {

    private final UserInformationRepository userInformationRepository;


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
