package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.RoleAuthoritiesDTO;
import com.fyp.erpapi.erpapi.data.RoleDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.NoSuchAuthorityException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
import com.fyp.erpapi.erpapi.repository.RoleRepository;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing roles within the application.
 * Provides functionality for creating roles and assigning authorities to them.
 */
@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    /**
     * Creates a new role with the specified details and returns its ID.
     *
     * @param roleDTO Data transfer object containing the name of the role to be created.
     * @return The ID of the newly created role.
     */
    public Long createAndReturnRoleId(RoleDTO roleDTO) {
        Role savedRole = roleRepository.save(new Role(roleDTO.getName()));
        return savedRole.getId();
    }


    /**
     * Retrieves a role by its name.
     *
     * @param roleName The name of the role to be retrieved.
     * @return The retrieved Role entity.
     * @throws NoSuchRoleException If no role with the specified name exists.
     */
    public Role getRoleByName(String roleName) throws NoSuchRoleException {
        Optional<Role> roleOptional = this.roleRepository.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new NoSuchRoleException(roleName);
        }
        return roleOptional.get();
    }

    /**
     * Assigns the specified authorities to a role.
     *
     * @param roleAuthoritiesDTO Data transfer object containing the role name and the authorities to be assigned to it.
     * @throws NoSuchRoleException If the role specified does not exist.
     */
    public void assignAuthorityToRole(RoleAuthoritiesDTO roleAuthoritiesDTO) throws NoSuchRoleException {
        Optional<Role> roleOptional = roleRepository.findByName(roleAuthoritiesDTO.getRoleName());
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            roleAuthoritiesDTO.getAuthorityNames().forEach(authorityName -> {
                Optional<Authority> authority = this.authorityRepository.findByName(authorityName);
                authority.ifPresent(value -> role.getAuthorities().add(value));
            });
            roleRepository.save(role);
        } else {
            throw new NoSuchRoleException(roleAuthoritiesDTO.getRoleName());
        }
    }
}
