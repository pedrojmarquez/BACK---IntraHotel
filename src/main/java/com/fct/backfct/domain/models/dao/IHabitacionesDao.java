package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Habitaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
