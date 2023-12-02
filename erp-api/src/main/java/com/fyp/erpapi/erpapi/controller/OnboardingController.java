package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.data.JoinCompanyDTO;
import com.fyp.erpapi.erpapi.data.OnBoardingCompleteDTO;
import com.fyp.erpapi.erpapi.data.UserInfoDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.service.CompanyService;
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

    private final UserService userService;
    private final CompanyService companyService;

    @PreAuthorize("hasRole('NON_ONBOARDED_USER_A')")
    @PostMapping(path = "/update/userInfo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        Long id = userInfoDTO.getId();
        String firstName = userInfoDTO.getFirstName();
        String lastName = userInfoDTO.getLastName();
        try {
            if (firstName != null && !firstName.isEmpty() && !firstName.equals(this.userService.getFirstName(id))) {
                this.userService.updateFirstName(id, firstName);
            }
            if (lastName != null && !lastName.isEmpty() && !lastName.equals(this.userService.getLastName(id))) {
                this.userService.updateLastName(id, lastName);
            }

            this.userService.updateRole(id, "NON_ONBOARDED_USER_B");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/joinCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> joinCompany(@RequestBody JoinCompanyDTO joinCompanyDTO) throws NoSuchRoleException {
        this.userService.joinCompany(joinCompanyDTO);
        this.userService.updateRole(joinCompanyDTO.getUserId(), "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/createCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCompany(@RequestBody CreateCompanyDTO createCompanyDTO) throws AlreadyExistsException {
        this.companyService.createCompany(createCompanyDTO);
        this.userService.updateRole(createCompanyDTO.getAdminId(), "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasRole('NON_ONBOARDED_USER_C')")
    @PostMapping(path = "/onboardingComplete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> completeOnboarding(@RequestBody OnBoardingCompleteDTO onBoardingCompleteDTO) throws NoSuchRoleException {
        // TODO - Add functionality to to validate this request. Is the user ready to complete onboarding?
        this.userService.validateOnboardingComplete(onBoardingCompleteDTO);
        this.userService.updateRole(onBoardingCompleteDTO.getId(), "USER");
        return ResponseEntity.ok().build();
    }
}
