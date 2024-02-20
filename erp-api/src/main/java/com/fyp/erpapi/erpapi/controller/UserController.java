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

@RestController
@RequestMapping("/user/get")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping(value = "{email}/userDetails", produces = "application/json")
    @PreAuthorize("#email == authentication.principal.attributes['email'] && hasRole('USER')")
    public ResponseEntity<UserDetailsDTO> getAllUserDetails(@PathVariable String email) {
        User user = (User) this.userService.loadUserByUsername(email);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user);
        return ResponseEntity.ok().body(userDetailsDTO);
    }


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
