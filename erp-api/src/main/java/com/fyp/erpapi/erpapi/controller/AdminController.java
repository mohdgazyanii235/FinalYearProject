package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.annotation.AdminActionAuthorize;
import com.fyp.erpapi.erpapi.annotation.AdminActionType;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {


    private final AuthorityService authorityService;
    private final RoleService roleService;
    private final UserService userService;

    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_AUTHORITIES)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewAuthority")
    public ResponseEntity<?> createNewAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return ResponseEntity.ok(authorityService.createAndReturnAuthorityId(authorityDTO));
    }

    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_ROLES)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewRole")
    public ResponseEntity<?> createNewRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.createAndReturnRoleId(roleDTO));
    }

    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_ROLES)
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


    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_ROLES)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignRoleToUser?userEmail={userEmail}")
    public ResponseEntity<?> assignRoleToUser(@RequestBody UserRoleDTO userRoleDTO, @PathVariable String userEmail) {
        userRoleDTO.setEmail(userEmail); // Doing this to prevent a major security flaw will be described in the report.
        try {
            this.userService.assignRolesToUser(userRoleDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
