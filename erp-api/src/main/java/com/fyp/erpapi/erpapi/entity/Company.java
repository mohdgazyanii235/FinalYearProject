package com.fyp.erpapi.erpapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * Entity representing a company in the system.
 * Companies can have multiple users but are managed by a single administrator.
 */
@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {

    /**
     * Unique identifier for the Company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the Company.
     */
    private String name;

    /**
     * Address of the Company.
     */
    private String address;

    /**
     * The administrator (User) of the Company.
     */
    @OneToOne
    private User admin;

}
