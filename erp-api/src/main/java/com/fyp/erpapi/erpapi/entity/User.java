package com.fyp.erpapi.erpapi.entity;

import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Entity representing a user in the system.
 * Implements UserDetails for integration with Spring Security.
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {


    /**
     * Unique identifier for the User.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * First name of the User.
     */
    private String firstName;

    /**
     * Last name of the User.
     */
    private String lastName;


    /**
     * Email of the User. Used as the username in UserDetails.
     */
    private String email;

    /**
     * Password of the User. Stored securely.
     */
    private String password;

    /**
     * Image URL for the User's profile picture.
     */
    private String imageUrl;

    /**
     * Indicates whether the user has completed the onboarding process.
     */
    private Boolean isOnboardingComplete = false;

    /**
     * Roles assigned to the User.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /**
     * Company to which the User belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    /**
     * Source of Single Sign-On, if used for authentication.
     */
    private SSOIssuer ssoIssuer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Role role : this.roles) {
            grantedAuthorityList.add(new CustomGrantedAuthority("ROLE_" + role.getName()));
            for (Authority authority : role.getAuthorities()) {
                grantedAuthorityList.add(new CustomGrantedAuthority(authority.getName()));
            }
        }
        return grantedAuthorityList;
    }

    /**
     * Checks if the user has a specific authority.
     *
     * @param authority The authority to check for.
     * @return true if the user has the specified authority, false otherwise.
     */
    public boolean hasAuthority(String authority) {
        for (GrantedAuthority grantedAuthority : this.getAuthorities()) {
            CustomGrantedAuthority customGrantedAuthority = (CustomGrantedAuthority) grantedAuthority;
            if (customGrantedAuthority.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user has a specific role.
     *
     * @param roleName The name of the role to check for.
     * @return true if the user has the specified role, false otherwise.
     */
    public boolean hasRole(String roleName) {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }


    /**
     * Adds a role to the user.
     *
     * @param role The role to be added to the user.
     */
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return  ((o instanceof User)
                && (Objects.equals(this.id, ((User) o).getId()))
                && (Objects.equals(this.email, ((User) o).getEmail()))
                && (this.getAuthorities().equals(((User) o).getAuthorities())));
    }

    /**
     * Checks if the user has the admin role.
     *
     * @return true if the user has the "ROLE_ADMIN" authority, false otherwise.
     */
    public boolean isAdmin() {
        return (this.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
