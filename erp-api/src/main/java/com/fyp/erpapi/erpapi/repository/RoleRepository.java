package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
