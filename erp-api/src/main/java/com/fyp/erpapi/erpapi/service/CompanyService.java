package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.repository.CompanyRepository;
import com.fyp.erpapi.erpapi.repository.RoleRepository;
import com.fyp.erpapi.erpapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;



//    This function is bad practice. It will be removed and a better solution will be implemented when the time comes.
//    It has been put in place to resolve circular dependencies.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }

    public Company getCompanyByName(String name) {
        return companyRepository.findByName(name);
    }

    public void createCompany(CreateCompanyDTO createCompanyDTO) throws AlreadyExistsException {
        if (this.existsByName(createCompanyDTO.getName())) {
            throw new AlreadyExistsException("Company name already exists");
        }
        User admin = (User) this.loadUserByUsername(createCompanyDTO.getEmail());
        Company company = new Company();
        company.setName(createCompanyDTO.getName());
        company.setAddress(createCompanyDTO.getAddress());
        company.setAdmin(admin);
        this.companyRepository.save(company);
        admin.setCompany(company);
        Role role = this.roleRepository.findByName("ADMIN").get();
        admin.addRole(role);

        this.userRepository.save(admin);

    }

}
