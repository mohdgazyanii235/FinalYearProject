package com.fyp.erpapi.erpapi.data;

import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDTO {



    private String email;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private Collection<CustomGrantedAuthority> roles;
    private CompanyDTO company;

    public UserDetailsDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.imageUrl = user.getImageUrl();
        this.roles = (Collection<CustomGrantedAuthority>) user.getAuthorities();
        this.company = new CompanyDTO(user.getCompany().getId(), user.getCompany().getName(), user.getCompany().getAddress());
    }
}
