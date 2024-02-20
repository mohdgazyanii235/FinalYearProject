package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByName(String name);
    Company findByName(String name);

    @Query("SELECT c.name FROM Company c WHERE c.id > 0")
    Set<String> getAllCompanyNames();
}
