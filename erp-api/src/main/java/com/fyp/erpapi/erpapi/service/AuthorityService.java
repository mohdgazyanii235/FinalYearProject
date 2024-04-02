package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.AuthorityDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling operations related to Authority entities.
 * This includes creating new authorities and other authority management functionalities.
 */
@Service
@AllArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    /**
     * Creates a new authority with the given details.
     *
     * @param authorityDTO Data Transfer Object containing the details of the authority to be created.
     * @return The ID of the newly created authority.
     */
    public Long createAndReturnAuthorityId(AuthorityDTO authorityDTO) {
        Authority savedAuthority = authorityRepository.save(new Authority(authorityDTO.getName()));
        return savedAuthority.getId();
    }

}
