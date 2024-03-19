package com.fyp.erpapi.erpapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a role within the system.
 * Roles are used to group authorities for assignment to users.
 */
@Entity
@Getter
@Setter
@Table(name = "role")
@NoArgsConstructor
public class Role {

    /**
     * Unique identifier for the Role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the Role.
     */
    private String name;

    /**
     * Authorities associated with the Role.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"), // Column name from the role table
            inverseJoinColumns = @JoinColumn(name = "authority_id") // Column name from the authority table
    )
    private Set<Authority> authorities;

    public Role(String name) {
        this.name = name;
    }


    /**
     * Adds an authority to the Role.
     *
     * @param authority The authority to be added to the Role.
     */
    public void addAuthority(Authority authority) {
        if (this.authorities == null) {
            this.authorities = new HashSet<>();
        }
        this.authorities.add(authority);
    }
}


