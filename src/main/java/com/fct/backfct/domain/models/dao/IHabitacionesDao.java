package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHabitacionesDao extends JpaRepository<Habitaciones, Long> {
}
