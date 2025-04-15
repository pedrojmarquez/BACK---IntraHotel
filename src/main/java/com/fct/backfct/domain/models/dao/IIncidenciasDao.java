package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Incidencias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIncidenciasDao extends JpaRepository<Incidencias, Long> {
}
