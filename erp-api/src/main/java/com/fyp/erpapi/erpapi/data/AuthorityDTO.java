package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing an authority.
 * Authorities are typically used in the context of security to represent permissions or roles.
 *
 * @author Lombok annotations provide
 * @AllArgsConstructor - Automatic constructor generation with all arguments.
 * @Getter - Automatic getter methods for all fields.
 * @Setter - Automatic setter methods for all fields.
 * @NoArgsConstructor - Automatic generation of a no-argument constructor.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AuthorityDTO {

    /**
     * The name of the authority.
     */
    private String name;

}
