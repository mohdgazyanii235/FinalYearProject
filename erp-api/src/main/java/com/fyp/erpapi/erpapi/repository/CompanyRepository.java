package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository interface for {@link Company} entities.
 * Provides methods for querying Company data from the database.
 */
@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * Checks if a company exists by its name.
     *
     * @param name The name of the company.
     * @return true if a company with the given name exists, false otherwise.
     */
    Boolean existsByName(String name);

    /**
     * Finds a company by its name.
     *
     * @param name The name of the company.
     * @return The Company entity if found, null otherwise.
     */
    Company findByName(String name);

    /**
     * Retrieves the names of all companies.
     *
     * @return A set containing the names of all companies.
     */
    @Query("SELECT c.name FROM Company c WHERE c.id > 0")
    Set<String> getAllCompanyNames();
}
