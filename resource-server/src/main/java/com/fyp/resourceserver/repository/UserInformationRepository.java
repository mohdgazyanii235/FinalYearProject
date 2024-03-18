package com.fyp.resourceserver.repository;

import com.fyp.resourceserver.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {

    Optional<UserInformation> getUserInformationByEmail(String email);

}
