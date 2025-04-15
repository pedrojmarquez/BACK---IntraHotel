package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.EstadosReserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoReservaDao extends JpaRepository<EstadosReserva, Long> {
}
