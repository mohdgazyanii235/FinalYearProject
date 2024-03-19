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


/**
 * Controller for handling administration-related actions.
 * Provides endpoints for managing authorities, roles, and their assignments to users within the application.
 * These endpoints are secured and require the caller to have administrative privileges.
 */
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {


    private final AuthorityService authorityService;
    private final RoleService roleService;
    private final UserService userService;


    /**
     * Creates a new authority within the system.
     *
     * @param authorityDTO Data transfer object containing information about the authority to be created.
     * @return ResponseEntity containing the ID of the newly created authority or an error message.
     */
    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_AUTHORITIES)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewAuthority")
    public ResponseEntity<?> createNewAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return ResponseEntity.ok(authorityService.createAndReturnAuthorityId(authorityDTO));
    }

    /**
     * Creates a new role within the system.
     *
     * @param roleDTO Data transfer object containing information about the role to be created.
     * @return ResponseEntity containing the ID of the newly created role or an error message.
     */
    @AdminActionAuthorize(actionSubjectEmail = "userEmail", actionType = AdminActionType.WRITE_ROLES)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createNewRole")
    public ResponseEntity<?> createNewRole(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.createAndReturnRoleId(roleDTO));
    }


    /**
     * Assigns an authority to a role.
     *
     * @param roleAuthoritiesDTO Data transfer object containing the role and the authorities to be assigned to it.
     * @return ResponseEntity indicating success or failure of the operation.
     */
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


    /**
     * Assigns a role to a user.
     *
     * @param userRoleDTO Data transfer object containing the roles to be assigned to the user.
     * @param userEmail The email of the user to whom the roles are to be assigned.
     * @return ResponseEntity indicating success or failure of the operation.
     */
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
