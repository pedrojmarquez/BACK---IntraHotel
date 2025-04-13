package com.fct.backfct.security.repository;


import com.fct.backfct.security.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findById(Long id);
}
