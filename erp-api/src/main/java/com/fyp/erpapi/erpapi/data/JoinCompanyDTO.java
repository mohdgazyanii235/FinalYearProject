package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) used when a user requests to join an existing company.
 * Contains information needed to associate a user with a company.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class JoinCompanyDTO {

    /**
     * The email of the user joining the company. This is used to identify the user within the system.
     */
    private String email;

    /**
     * The name of the company the user wishes to join.
     */
    private String companyName;
}
