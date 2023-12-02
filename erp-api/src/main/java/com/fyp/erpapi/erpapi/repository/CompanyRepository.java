package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByName(String name);
    Company findByName(String name);
}
