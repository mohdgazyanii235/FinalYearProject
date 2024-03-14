package com.fyp.erpapi.erpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ErpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApiApplication.class, args);
    }

}
