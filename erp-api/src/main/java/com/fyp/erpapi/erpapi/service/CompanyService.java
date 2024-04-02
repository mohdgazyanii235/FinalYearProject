package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service for handling operations related to Company entities.
 * This includes checking for existing companies, retrieving company details, and managing company listings.
 */
@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Retrieves a company by its name.
     *
     * @param name The name of the company.
     * @return The Company entity if found.
     */
    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    /**
     * Retrieves the names of all companies.
     *
     * @return A Set containing the names of all companies.
     */
    public Set<String> getAllCompanyNames() {
        return this.companyRepository.getAllCompanyNames();
    }



}
