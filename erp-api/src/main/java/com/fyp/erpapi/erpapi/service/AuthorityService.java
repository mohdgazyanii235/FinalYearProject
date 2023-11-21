package com.fyp.erpapi.erpapi.service;

import com.fyp.erpapi.erpapi.data.AuthorityDTO;
import com.fyp.erpapi.erpapi.entity.Authority;
import com.fyp.erpapi.erpapi.repository.AuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public Long createAndReturnAuthorityId(AuthorityDTO authorityDTO) {
        Authority savedAuthority = authorityRepository.save(new Authority(authorityDTO.getName()));
        return savedAuthority.getId();
    }

}
