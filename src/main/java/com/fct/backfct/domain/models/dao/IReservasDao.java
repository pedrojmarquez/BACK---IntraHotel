package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IReservasDao extends JpaRepository<Reservas, Long> {

    @Query("SELECT r FROM Reservas r ORDER BY r.fechaReserva DESC")
    List<Reservas> getReservasOrdenadasPorFecha();
}
