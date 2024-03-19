package com.fyp.erpapi.erpapi.controller;

import com.fyp.erpapi.erpapi.entity.Company;
import com.fyp.erpapi.erpapi.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/**
 * Controller for managing company-related operations.
 * This controller provides endpoints for fetching information about companies within the system.
 */
@RestController
@RequestMapping("/company/get")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    /**
     * Retrieves a list of all company names.
     *
     * This endpoint returns a set of all company names available in the system. It's accessible
     * to users with appropriate permissions, ensuring that sensitive company information is
     * protected.
     *
     * @return ResponseEntity containing a set of company names. The response is always in JSON format.
     */
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Set<String>> getAllCompanies() {
        return ResponseEntity.ok().body(this.companyService.getAllCompanyNames());
    }

}
