package com.fyp.erpapi.erpapi.data;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * Data Transfer Object (DTO) encapsulating detailed information about a user.
 * Includes personal information, roles, and company association.
 */
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

    /**
     * Constructs a UserDetailsDTO from a User entity.
     * Maps user entity fields to DTO fields, including converting the user's roles and company details.
     *
     * @param user The User entity from which to construct the UserDetailsDTO.
     */
    public UserDetailsDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.imageUrl = user.getImageUrl();
        this.roles = (Collection<CustomGrantedAuthority>) user.getAuthorities();
        this.company = new CompanyDTO(user.getCompany().getId(), user.getCompany().getName(), user.getCompany().getAddress());
    }
}
