package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {


}
