package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;

    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }

    public void createCompany(CreateCompanyDTO createCompanyDTO) throws AlreadyExistsException {
        if (this.existsByName(createCompanyDTO.getName())) {
            throw new AlreadyExistsException("Company name already exists");
        }
        User admin = (User) this.userService.loadUserById(createCompanyDTO.getAdminId());
        Company company = new Company();
        company.setName(createCompanyDTO.getName());
        company.setAddress(createCompanyDTO.getAddress());
        company.setAdmin(admin);
        admin.setCompany(company);

        this.companyRepository.save(company);
        this.userService.saveUser(admin);

    }

}
