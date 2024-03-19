package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new company.
 * Contains information required to register a new company within the system.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCompanyDTO {

    /**
     * The email of the user creating the company. Used to link the company to the creator.
     */
    private String email;

    /**
     * The name of the new company.
     */
    private String name;

    /**
     * The address of the new company.
     */
    private String address;
}
