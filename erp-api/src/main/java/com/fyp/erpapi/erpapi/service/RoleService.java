package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.RoleDTO;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Long createAndReturnRoleId(RoleDTO roleDTO) {
        Role savedRole = roleRepository.save(new Role(roleDTO.getName()));
        return savedRole.getId();
    }

}
