package com.fct.backfct.domain.models.dao;

import com.fct.backfct.security.entity.Empleados;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmpleadosDao extends JpaRepository<Empleados, Long> {
    Optional<Empleados> findByEmail(String email);

    boolean existsByEmail(String email);

}
