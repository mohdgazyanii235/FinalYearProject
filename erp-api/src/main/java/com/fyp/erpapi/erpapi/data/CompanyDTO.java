package com.fyp.erpapi.erpapi.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for companies.
 * This class is used to transfer company data across different layers of the application,
 * especially for operations involving company details without exposing entity models.
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO {

    /**
     * The unique identifier of the company.
     */
    private Long id;

    /**
     * The name of the company.
     */
    private String companyName;

    /**
     * The address of the company.
     */
    private String companyAddress;
}
