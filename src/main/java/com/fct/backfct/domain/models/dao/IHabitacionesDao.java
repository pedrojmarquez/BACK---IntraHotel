package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IHabitacionesDao extends JpaRepository<Habitaciones, Long> {

    @Query("""
    SELECT h FROM Habitaciones h
    WHERE h.idHabitacion NOT IN (
        SELECT r.habitacion.idHabitacion FROM Reservas r
        WHERE r.fechaEntrada < :fechaSalida
          AND r.fechaSalida > :fechaEntrada
    )
    AND (:tipo IS NULL OR h.tipo = :tipo)
    AND (:capacidad IS NULL OR h.capacidad = :capacidad)
""")
    List<Habitaciones> findHabitacionesDisponibles(
            @Param("fechaEntrada") LocalDateTime fechaEntrada,
            @Param("fechaSalida") LocalDateTime fechaSalida,
            @Param("tipo") String tipo,
            @Param("capacidad") Integer capacidad
    );


    @Query("""
    SELECT h FROM Habitaciones h
    WHERE h.estadoHabitacion.idEstado IN (3, 5)
    AND (:planta IS NULL OR h.planta = :planta)
    ORDER BY h.numeroHabitacion
""")
    List<Habitaciones> findHabitacionesLimpieza(@Param("planta") Integer planta);


    // Búsqueda para tipoBusqueda = "habitacion"
    @Query("SELECT h FROM Habitaciones h WHERE " +
            "(:capacidad IS NULL OR h.capacidad = :capacidad) AND " +
            "(:estado IS NULL OR h.estadoHabitacion.nombre = :estado) AND " +
            "(:numeroHabitacion IS NULL OR h.numeroHabitacion = :numeroHabitacion) AND " +
            "(:tipo IS NULL OR h.tipo = :tipo) AND " +
            "(:planta IS NULL OR h.planta = :planta)"+
            "ORDER BY h.numeroHabitacion"
    )

    List<Habitaciones> buscarPorFiltrosHabitacion(
            @Param("capacidad") Integer capacidad,
            @Param("estado") String estado,
            @Param("numeroHabitacion") String numeroHabitacion,
            @Param("tipo") String tipo,
            @Param("planta") Integer planta);

    // Búsqueda para tipoBusqueda = "cliente"
    @Query("SELECT h FROM Habitaciones h JOIN Reservas r JOIN r.cliente c WHERE " +
            "(:nombreCliente IS NULL OR c.nombre LIKE %:nombreCliente%) AND " +
            "(:dniCliente IS NULL OR c.dni = :dniCliente)")
    List<Habitaciones> buscarPorFiltrosCliente(
            @Param("nombreCliente") String nombreCliente,
            @Param("dniCliente") String dniCliente);

    // Búsqueda para tipoBusqueda = "reserva"
    @Query("SELECT h FROM Habitaciones h JOIN Reservas r WHERE " +
            "(:fechaEntrada IS NULL OR r.fechaEntrada = :fechaEntrada) AND " +
            "(:fechaSalida IS NULL OR r.fechaSalida = :fechaSalida) AND " +
            "(:estadoReserva IS NULL OR r.estadoReserva = :estadoReserva)")
    List<Habitaciones> buscarPorFiltrosReserva(
            @Param("fechaEntrada") LocalDate fechaEntrada,
            @Param("fechaSalida") LocalDate fechaSalida,
            @Param("estadoReserva") String estadoReserva);

}
