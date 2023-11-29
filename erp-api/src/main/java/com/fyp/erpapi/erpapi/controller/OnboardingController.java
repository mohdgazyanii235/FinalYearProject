package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.data.OnBoardingCompleteDTO;
import com.fyp.erpapi.erpapi.data.UserInfoDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding")
@AllArgsConstructor
public class OnboardingController {

    /*
    Following endpoints needed:
    1) /onboarding/{userId}/userInfo (POST/PUT) giving users the ability to updated things like firstname, lastname, etc.
    2) /onboarding/{userId}/companyInfo (POST/PUT) giving users the ability to join company and set their position - some functionality of approval to be added here.
    3) /onboarding/{userId}/profileInfo (POST/PUT) giving users the ability to set their profile picture, etc.
     */

    private final UserService userService;

    @PreAuthorize("hasRole('NON_ONBOARDED_USER_A')")
    @PostMapping(path = "update/userInfo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        Long id = userInfoDTO.getId();
        String firstName = userInfoDTO.getFirstName();
        String lastName = userInfoDTO.getLastName();
        try {
            if (firstName != null && !firstName.isEmpty() && !firstName.equals(this.userService.getFirstName(id))) {
                this.userService.updateFirstName(id, firstName);
            }
            if (lastName != null && !lastName.isEmpty() && !lastName.equals(this.userService.getLastName(id))) {
                this.userService.updateFirstName(id, lastName);
            }

            this.userService.updateRole(id, "ROLE_NON_ONBOARDED_USER_B");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO - Create Company Table to add functionality here.
    @PreAuthorize("hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "update/createCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCompany(CreateCompanyDTO createCompanyDTO) {
        this.userService.updateRole(createCompanyDTO.getId(), "ROLE_NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('NON_ONBOARDED_USER_C')")
    @PostMapping(path = "update/onBoardingComplete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCompany(OnBoardingCompleteDTO onBoardingCompleteDTO) {
        // TODO - Add functionality to to validate this request. Is the user ready to complete onboarding?
        this.userService.updateRole(onBoardingCompleteDTO.getId(), "USER");
        return ResponseEntity.ok().build();
    }
}
