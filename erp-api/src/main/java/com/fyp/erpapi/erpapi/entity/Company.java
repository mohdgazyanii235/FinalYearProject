package com.fyp.erpapi.erpapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {

    @Id
    private Long id;

    private String name;

    private String address;

    @OneToOne
    private User admin;

}
