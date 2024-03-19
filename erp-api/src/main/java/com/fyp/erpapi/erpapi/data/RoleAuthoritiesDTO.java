package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for associating multiple authorities with a specific role.
 * Facilitates the management of permissions by assigning a list of authorities to a role.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RoleAuthoritiesDTO {

    /**
     * The name of the role to which authorities are being assigned.
     */
    private String roleName;

    /**
     * A list of names representing the authorities to be assigned to the role.
     */
    private List<String> authorityNames;
}
