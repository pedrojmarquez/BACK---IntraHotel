package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.EstadosHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadosHabitacionDao extends JpaRepository<EstadosHabitacion, Long> {
}
