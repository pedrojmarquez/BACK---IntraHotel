package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.EstadosIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadosIncidenciaDao extends JpaRepository<EstadosIncidencia, Long> {
}
