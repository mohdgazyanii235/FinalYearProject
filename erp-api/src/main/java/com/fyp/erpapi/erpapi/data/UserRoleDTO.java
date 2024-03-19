package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for assigning roles to a user.
 * This class is used in the context of updating or assigning new roles to users within the application.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserRoleDTO {

    /**
     * The email of the user to whom the roles will be assigned.
     */
    private String email;

    /**
     * The names of the roles to be assigned to the user.
     */
    private List<String> roleNames;

}
