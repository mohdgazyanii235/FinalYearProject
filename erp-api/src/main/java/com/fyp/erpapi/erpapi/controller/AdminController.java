package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.data.AuthorityDTO;
import com.fyp.erpapi.erpapi.data.RoleDTO;
import com.fyp.erpapi.erpapi.service.AuthorityService;
import com.fyp.erpapi.erpapi.service.RoleService;
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
}
