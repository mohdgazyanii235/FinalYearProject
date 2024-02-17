package com.fyp.erpapi.erpapi.service;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@ToString
public class CustomGrantedAuthority implements GrantedAuthority {

    private String grantedAuthority; // for example: ROLE_ADMIN (role) or JOIN_COMPANY (authority)

    @Override
    public String getAuthority() {
        return this.grantedAuthority;
    }
}
