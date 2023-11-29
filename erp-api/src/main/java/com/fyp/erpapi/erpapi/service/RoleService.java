package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.RoleAuthoritiesDTO;
import com.fyp.erpapi.erpapi.data.RoleDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.exception.NoSuchAuthorityException;
import com.fyp.erpapi.erpapi.exception.NoSuchRoleException;
import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
import com.fyp.erpapi.erpapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    public Long createAndReturnRoleId(RoleDTO roleDTO) {
        Role savedRole = roleRepository.save(new Role(roleDTO.getName()));
        return savedRole.getId();
    }

    public Role getRoleByName(String roleName) throws NoSuchRoleException {
        Optional<Role> roleOptional = this.roleRepository.findByName(roleName);
        if (roleOptional.isEmpty()) {
            throw new NoSuchRoleException(roleName);
        }
        return roleOptional.get();
    }

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
