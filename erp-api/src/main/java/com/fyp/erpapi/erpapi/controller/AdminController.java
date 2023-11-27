package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.AuthorityDTO;
import com.fyp.erpapi.erpapi.data.RoleAuthoritiesDTO;
import com.fyp.erpapi.erpapi.data.RoleDTO;
import com.fyp.erpapi.erpapi.data.UserRoleDTO;
import com.fyp.erpapi.erpapi.service.AuthorityService;
import com.fyp.erpapi.erpapi.service.RoleService;
import com.fyp.erpapi.erpapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    /*
    1) Create new Authorities
    2) Create new Roles
    3) Assign Authorities to Roles
    4) Assign Roles to Users
     */

    private final AuthorityService authorityService;
    private final RoleService roleService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewAuthority")
    public ResponseEntity<?> createNewAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return ResponseEntity.ok(authorityService.createAndReturnAuthorityId(authorityDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewRole")
    public ResponseEntity<?> createNewRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.createAndReturnRoleId(roleDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignAuthorityToRole")
    public ResponseEntity<?> assignAuthorityToRole(@RequestBody RoleAuthoritiesDTO roleAuthoritiesDTO) {
        try {
            this.roleService.assignAuthorityToRole(roleAuthoritiesDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignRoleToUser")
    public ResponseEntity<?> assignRoleToUser(@RequestBody UserRoleDTO userRoleDTO) {
        try {
            this.userService.assignRolesToUser(userRoleDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
