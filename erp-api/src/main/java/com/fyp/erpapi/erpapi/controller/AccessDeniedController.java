package com.fyp.erpapi.erpapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accessDenied")
public class AccessDeniedController {

    @GetMapping
    public ResponseEntity<?> accessDenied() {
        return ResponseEntity.status(403).body("Access Denied");
    }

}
