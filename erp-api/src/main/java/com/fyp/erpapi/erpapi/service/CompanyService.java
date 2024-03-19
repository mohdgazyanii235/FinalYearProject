package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }

    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    public Set<String> getAllCompanyNames() {
        return this.companyRepository.getAllCompanyNames();
    }



}
