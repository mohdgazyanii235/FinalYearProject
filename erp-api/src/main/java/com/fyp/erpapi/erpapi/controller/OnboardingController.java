package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.data.JoinCompanyDTO;
import com.fyp.erpapi.erpapi.data.OnBoardingCompleteDTO;
import com.fyp.erpapi.erpapi.data.UserInfoDTO;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.exception.NoSuchCompanyException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.service.AdminService;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing the onboarding process of users.
 * This includes updating user information, joining or creating a company,
 * and marking the onboarding process as complete. Access to these endpoints
 * is secured and requires specific roles during different stages of the onboarding process.
 */
@RestController
@RequestMapping("/onboarding")
@AllArgsConstructor
public class OnboardingController {

    private final UserService userService;
    private final AdminService adminService;


    /**
     * Test endpoint to check the functionality and retrieve the email from the security context.
     *
     * @param email The email of the user.
     * @return ResponseEntity with OK status.
     */
    @GetMapping(path = "/test/{email}")
    public ResponseEntity<?> test(@PathVariable String email) {
        DefaultOidcUser oidcUser = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(oidcUser.getAttributes().get("email"));
        System.out.println("User email: " + email);
        return ResponseEntity.ok().build();
    }


    /**
     * Updates the user's personal information during the onboarding process.
     * This endpoint requires the user to be in a specific onboarding role.
     *
     * @param userInfoDTO Data transfer object containing the user's personal information.
     * @param email The email of the user being updated.
     * @return ResponseEntity indicating the success or failure of the update operation.
     */
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_A')")
    @PostMapping(path = "/{email}/update/userInfo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO, @PathVariable String email) {
        String firstName = userInfoDTO.getFirstName();
        String lastName = userInfoDTO.getLastName();
        System.out.println(firstName);
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

    /**
     * Allows a user to join an existing company during the onboarding process.
     * The user's role is updated upon successfully joining a company.
     *
     * @param joinCompanyDTO Data transfer object containing the company details.
     * @param email The email of the user joining the company.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/{email}/joinCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> joinCompany(@RequestBody JoinCompanyDTO joinCompanyDTO, @PathVariable String email) throws NoSuchRoleException, NoSuchCompanyException {
        joinCompanyDTO.setEmail(email);
        this.userService.joinCompany(joinCompanyDTO);
        this.userService.updateRole(email, "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    /**
     * Allows a user to create a new company during the onboarding process.
     * The user's role is updated upon successfully creating a company.
     *
     * @param createCompanyDTO Data transfer object containing the new company details.
     * @param email The email of the user creating the company.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_B')")
    @PostMapping(path = "/{email}/createCompany", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCompany(@RequestBody CreateCompanyDTO createCompanyDTO, @PathVariable String email) throws AlreadyExistsException {
        createCompanyDTO.setEmail(email);
        this.adminService.createCompany(createCompanyDTO);
        this.userService.updateRole(email, "NON_ONBOARDED_USER_C");
        return ResponseEntity.ok().build();
    }

    /**
     * Marks the onboarding process as complete for a user.
     * This endpoint updates the user's role to a regular user role, indicating they have completed onboarding.
     *
     * @param onBoardingCompleteDTO Data transfer object indicating the onboarding is complete.
     * @param email The email of the user completing the onboarding process.
     * @return ResponseEntity indicating the success or failure of marking the onboarding as complete.
     */
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('NON_ONBOARDED_USER_C')")
    @PostMapping(path = "/{email}/onboardingComplete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> completeOnboarding(@RequestBody OnBoardingCompleteDTO onBoardingCompleteDTO, @PathVariable String email) throws NoSuchRoleException {
        onBoardingCompleteDTO.setEmail(email);
        this.userService.validateOnboardingComplete(onBoardingCompleteDTO);
        this.userService.updateRole(email, "USER");
        return ResponseEntity.ok().build();
    }
}
