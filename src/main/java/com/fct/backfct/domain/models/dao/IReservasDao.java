package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservasDao extends JpaRepository<Reservas, Long> {

    @Query("SELECT r FROM Reservas r ORDER BY r.fechaReserva DESC")
    List<Reservas> getReservasOrdenadasPorFecha();

    List<Reservas> findTop5ByHabitacion_IdHabitacionOrderByFechaReservaDesc(Long idHabitacion);

    List<Reservas> findTop3ByCliente_IdClienteOrderByFechaReservaDesc(Long idCliente);

    @Query("SELECT r FROM Reservas r WHERE r.habitacion.idHabitacion = :habitacionId AND r.fechaEntrada > :fechaActual ORDER BY r.fechaEntrada ASC")
    List<Reservas> findSiguientesReservas(@Param("habitacionId") Long habitacionId, @Param("fechaActual") LocalDateTime fechaActual);

    @Query("SELECT COUNT(r) FROM Reservas r WHERE r.estadoReserva.idEstadoReserva = 1")
    Integer countReservasPendientes();
}
