package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for {@link Authority} entities.
 * Provides methods for performing operations on the database for Authority entities.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    /**
     * Finds an Authority by its name.
     *
     * @param name The name of the Authority.
     * @return An Optional containing the Authority if found, or an empty Optional otherwise.
     */
    Optional<Authority> findByName(String name);

}
