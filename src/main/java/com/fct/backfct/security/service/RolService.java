package com.fct.backfct.security.service;


import com.fct.backfct.security.entity.Roles;
import com.fct.backfct.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Roles getById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }
}
