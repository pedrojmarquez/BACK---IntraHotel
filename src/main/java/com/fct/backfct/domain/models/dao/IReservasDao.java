package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservasDao extends JpaRepository<Reservas, Long> {
}
