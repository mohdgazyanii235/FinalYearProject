package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a role.
 * Roles are used to assign access controls and permissions to users within the application.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {

     /**
     * The name of the role.
     * */
    private String name;
}
