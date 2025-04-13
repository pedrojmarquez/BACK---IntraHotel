package com.fct.backfct.security.repository;


import com.fct.backfct.security.entity.Roles;
import com.fct.backfct.security.entity.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Empleados, Long> {


    Optional<Empleados> findByEmail(String email);

    boolean existsByEmail(String email);

}
