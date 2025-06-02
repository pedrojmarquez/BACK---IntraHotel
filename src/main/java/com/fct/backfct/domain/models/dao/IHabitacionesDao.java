package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IHabitacionesDao extends JpaRepository<Habitaciones, Long> {

    @Query("""
    SELECT h FROM Habitaciones h
    WHERE h.idHabitacion NOT IN (
        SELECT r.habitacion.idHabitacion FROM Reservas r
        WHERE r.fechaEntrada < :fechaSalida
          AND r.fechaSalida > :fechaEntrada
          AND r.estadoReserva.idEstadoReserva IN (1,2,5)
    )
    AND (:tipo IS NULL OR h.tipo = :tipo)
    AND (:capacidad IS NULL OR h.capacidad = :capacidad)
    AND h.estadoHabitacion.idEstado IN (1,2,5)
    ORDER BY h.numeroHabitacion
""")
    List<Habitaciones> findHabitacionesDisponibles(
            @Param("fechaEntrada") LocalDateTime fechaEntrada,
            @Param("fechaSalida") LocalDateTime fechaSalida,
            @Param("tipo") String tipo,
            @Param("capacidad") Integer capacidad
    );


    @Query("""
    SELECT h FROM Habitaciones h
    WHERE
      (
        h.estadoHabitacion.idEstado = 3 OR
        (h.estadoHabitacion.idEstado IN (2,5) AND h.limpiezaDiaria = 1)
      )
    AND (:planta IS NULL OR h.planta = :planta)
    ORDER BY h.numeroHabitacion
""")
    List<Habitaciones> findHabitacionesLimpieza(@Param("planta") Integer planta);

    // Metodo para obtener todas las habitaciones para hacerle el mantenimiento
    @Query("""
    SELECT h FROM Habitaciones h
    WHERE h.estadoHabitacion.idEstado = 4
    AND EXISTS (
        SELECT i FROM Incidencias i
        WHERE i.idHabitacion = h.idHabitacion AND i.idEstadoIncidencia = 3
    )
    AND (:planta IS NULL OR h.planta = :planta)
""")
    List<Habitaciones> findHabitacionesMantenimiento(@Param("planta") Integer planta);



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
    @Query("SELECT r.habitacion " +
            "FROM Reservas r " +
            "JOIN r.cliente c " +
            "WHERE r.estadoReserva.idEstadoReserva in(1, 2) " +
            "AND (:nombreCliente IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombreCliente, '%'))) " +
            "AND (:dniCliente IS NULL OR c.dni = :dniCliente) " +
            "AND (:apellidos IS NULL OR LOWER(c.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))) " +
            "AND (:telefono IS NULL OR c.telefono = :telefono)")
    List<Habitaciones> buscarPorFiltrosCliente(
            @Param("nombreCliente") String nombreCliente,
            @Param("dniCliente") String dniCliente,
            @Param("apellidos") String apellidos,
            @Param("telefono") String telefono);





    // Búsqueda para tipoBusqueda = "reserva"
    @Query("""
    SELECT r.habitacion
    FROM Reservas r
    WHERE (:fechaEntrada IS NULL OR FUNCTION('DATE', r.fechaEntrada) = :fechaEntrada)
    AND (:fechaSalida IS NULL OR FUNCTION('DATE', r.fechaSalida) = :fechaSalida)
    AND (:fechaReserva IS NULL OR FUNCTION('DATE', r.fechaReserva) = :fechaReserva)
    AND (:estadoReserva IS NULL OR r.estadoReserva.idEstadoReserva = :estadoReserva)
""")
    List<Habitaciones> buscarPorFiltrosReserva(
            @Param("fechaEntrada") Date fechaEntrada,
            @Param("fechaSalida") Date fechaSalida,
            @Param("fechaReserva") Date fechaReserva,
            @Param("estadoReserva") Long estadoReserva);



    // Metodo para proceso automatico de limpieza diaria
    @Modifying
    @Query("UPDATE Habitaciones h SET h.limpiezaDiaria = 1 WHERE h.estadoHabitacion.idEstado = 2")
    void actualizarLimpiezaDiariaParaOcupadas();


    @Query("SELECT COUNT(h) FROM Habitaciones h WHERE h.estadoHabitacion.idEstado in (2, 5)")
    Integer countHabitacionesOcupadas();

    @Query("SELECT COUNT(h) FROM Habitaciones h WHERE h.estadoHabitacion.idEstado = 4")
    Integer countHabitacionesMantenimiento();
}
