package com.fyp.erpapi.erpapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an authority within the system.
 * Authorities are typically permissions granted to users to perform certain actions.
 */
@Entity
@Getter
@Setter
@Table(name = "authority")
@NoArgsConstructor
public class Authority {

    /**
     * Unique identifier for the Authority.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the Authority. Represents the permission or role name.
     */
    private String name;

    /**
     * Constructs a new Authority with the specified name.
     *
     * @param name The name of the authority.
     */
    public Authority(String name) {
        this.name = name;
    }
}
