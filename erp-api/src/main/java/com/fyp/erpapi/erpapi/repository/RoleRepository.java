package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Role} entities.
 * Provides methods for accessing and manipulating Role data in the database.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a Role by its name.
     *
     * @param name The name of the Role.
     * @return An Optional containing the Role if found, or an empty Optional otherwise.
     */
    Optional<Role> findByName(String name);
}
