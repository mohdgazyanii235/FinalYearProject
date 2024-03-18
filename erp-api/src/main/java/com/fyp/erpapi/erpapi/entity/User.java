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


@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String imageUrl;

    private Boolean isOnboardingComplete = false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

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

    public boolean hasAuthority(String authority) {
        for (GrantedAuthority grantedAuthority : this.getAuthorities()) {
            CustomGrantedAuthority customGrantedAuthority = (CustomGrantedAuthority) grantedAuthority;
            if (customGrantedAuthority.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(String roleName) {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }


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

    public boolean isAdmin() {
        return (this.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
