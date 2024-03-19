package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.UserDetailsDTO;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller for user-related operations.
 * Provides endpoints for fetching user details and roles, ensuring that access is secured
 * and limited to the user themselves or users with appropriate permissions.
 */
@RestController
@RequestMapping("/user/get")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves the details of the user identified by the given email.
     * This endpoint is secured and can only be accessed by the user themselves.
     *
     * @param email The email of the user whose details are to be retrieved.
     * @return A ResponseEntity containing the UserDetailsDTO of the requested user.
     */
    @GetMapping(value = "{email}/userDetails", produces = "application/json")
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('USER')")
    public ResponseEntity<UserDetailsDTO> getAllUserDetails(@PathVariable String email) {
        User user = (User) this.userService.loadUserByUsername(email);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user);
        return ResponseEntity.ok().body(userDetailsDTO);
    }

    /**
     * Retrieves the roles of the user identified by the given email.
     * This endpoint allows users to fetch their own roles based on their email.
     *
     * @param email The email of the user whose roles are to be retrieved.
     * @return A ResponseEntity containing a list of roles associated with the user.
     */
    @GetMapping("/{email}/roles")
    @PreAuthorize("#email == authentication.principal.attributes['email']")
    public ResponseEntity<?> getUserRoles(@PathVariable String email) {
        User user = (User) this.userService.loadUserByUsername(email);
        Collection<CustomGrantedAuthority> grantedAuthorities = (Collection<CustomGrantedAuthority>) user.getAuthorities();

        List<String> roles = grantedAuthorities.stream()
                .map(CustomGrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(roles);
    }

}
