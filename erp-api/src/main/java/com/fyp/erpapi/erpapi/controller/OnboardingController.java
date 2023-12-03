package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.data.JoinCompanyDTO;
import com.fyp.erpapi.erpapi.data.OnBoardingCompleteDTO;
import com.fyp.erpapi.erpapi.data.UserInfoDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.exception.NoSuchCompanyException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.service.AdminService;
import com.fyp.erpapi.erpapi.service.CompanyService;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding")
@AllArgsConstructor
public class OnboardingController {

    private final UserService userService;
    private final AdminService adminService;


    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_A')")
    @GetMapping(path = "/test/{email}")
    public ResponseEntity<?> test(@PathVariable String email) {
        DefaultOidcUser oidcUser = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(oidcUser.getAttributes().get("email"));
        System.out.println("User email: " + email);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_A')")
    @PostMapping(path = "/{email}/update/userInfo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO, @PathVariable String email) {
        String firstName = userInfoDTO.getFirstName();
        String lastName = userInfoDTO.getLastName();
        try {
            if (firstName != null && !firstName.isEmpty() && !firstName.equals(this.userService.getFirstName(email))) {
                this.userService.updateFirstName(email, firstName);
            }
            if (lastName != null && !lastName.isEmpty() && !lastName.equals(this.userService.getLastName(email))) {
                this.userService.updateLastName(email, lastName);
            }

            this.userService.updateRole(email, "NON_ONBOARDED_USER_B");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/{email}/joinCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> joinCompany(@RequestBody JoinCompanyDTO joinCompanyDTO, @PathVariable String email) throws NoSuchRoleException, NoSuchCompanyException {
        joinCompanyDTO.setEmail(email);
        this.userService.joinCompany(joinCompanyDTO);
        this.userService.updateRole(email, "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/{email}/createCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCompany(@RequestBody CreateCompanyDTO createCompanyDTO, @PathVariable String email) throws AlreadyExistsException {
        createCompanyDTO.setEmail(email);
        this.adminService.createCompany(createCompanyDTO);
        this.userService.updateRole(email, "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_C')")
    @PostMapping(path = "/{email}/onboardingComplete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> completeOnboarding(@RequestBody OnBoardingCompleteDTO onBoardingCompleteDTO, @PathVariable String email) throws NoSuchRoleException {
        onBoardingCompleteDTO.setEmail(email);
        this.userService.validateOnboardingComplete(onBoardingCompleteDTO);
        this.userService.updateRole(email, "USER");
        return ResponseEntity.ok().build();
    }
}
