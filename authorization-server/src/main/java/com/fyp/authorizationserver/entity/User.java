package com.fyp.authorizationserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * Represents a user entity in the database.
 * <p>
 * This entity is used for authentication and authorization purposes within the Spring Security framework.
 * Implements {@link UserDetails} to provide user information to Spring Security.
 * </p>
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {


    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * The user's email address, used as the username for authentication.
     */
    private String email;

    /**
     * The user's password, used for authentication.
     */
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
