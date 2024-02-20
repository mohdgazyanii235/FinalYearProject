package com.fyp.erpapi.erpapi.data;

import com.fyp.erpapi.erpapi.entity.Role;
import com.fyp.erpapi.erpapi.service.CustomGrantedAuthority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserProfileDataDTO {
    private String email;
}
