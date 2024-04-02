package com.fyp.erpapi.erpapi.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

/**
 * Custom implementation of {@link GrantedAuthority} to represent authorities granted to a user.
 * This class allows for more flexible authority representations within the security context.
 */
@AllArgsConstructor
@ToString
public class CustomGrantedAuthority implements GrantedAuthority {

    private String grantedAuthority; // for example: ROLE_ADMIN (role) or JOIN_COMPANY (authority)

    /**
     * Returns the authority granted to the user.
     *
     * @return A string representation of the granted authority.
     */
    @Override
    public String getAuthority() {
        return this.grantedAuthority;
    }
}
