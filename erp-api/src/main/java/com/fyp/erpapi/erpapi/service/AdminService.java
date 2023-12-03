package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.CreateCompanyDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.exception.AlreadyExistsException;
import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
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
public class AdminService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    private UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    // TODO: Put this in a json config file such that it can be edited without recompiling
    private Role createAdminRolesAndAuthorities(String companyName) {
        Authority writeCompanyAuthorities = new Authority("WRITE_" + companyName.toUpperCase() + "_AUTHORITIES");
        Authority readCompanyAuthorities = new Authority("READ_" + companyName.toUpperCase() + "_AUTHORITIES");
        Authority writeCompanyRoles = new Authority("WRITE_" + companyName.toUpperCase() + "_ROLES");
        Authority readCompanyRoles = new Authority("READ_" + companyName.toUpperCase() + "_ROLES");
        this.authorityRepository.save(writeCompanyAuthorities);
        this.authorityRepository.save(readCompanyAuthorities);
        this.authorityRepository.save(writeCompanyRoles);
        this.authorityRepository.save(readCompanyRoles);

        Role admin = new Role("ADMIN");
        admin.addAuthority(writeCompanyAuthorities);
        admin.addAuthority(readCompanyAuthorities);
        admin.addAuthority(writeCompanyRoles);
        admin.addAuthority(readCompanyRoles);
        this.roleRepository.save(admin);
        return admin;
    }

    public void createCompany(CreateCompanyDTO createCompanyDTO) throws AlreadyExistsException {
        if (this.companyRepository.existsByName(createCompanyDTO.getName())) {
            throw new AlreadyExistsException("Company name already exists");
        }
        User admin = (User) this.loadUserByUsername(createCompanyDTO.getEmail());
        Company company = new Company();
        company.setName(createCompanyDTO.getName());
        company.setAddress(createCompanyDTO.getAddress());
        company.setAdmin(admin);
        this.companyRepository.save(company);
        admin.setCompany(company);
        admin.addRole(this.createAdminRolesAndAuthorities(company.getName()));
        this.userRepository.save(admin);
    }
}
