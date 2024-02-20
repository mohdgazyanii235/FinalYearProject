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

@RestController
@RequestMapping("/company/get")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Set<String>> getAllCompanies() {
        return ResponseEntity.ok().body(this.companyService.getAllCompanyNames());
    }

}
