package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Incidencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IIncidenciasDao extends JpaRepository<Incidencias, Long> {

    @Query("SELECT i FROM Incidencias i WHERE i.idEstadoIncidencia = :estadoIncidencia")
    List<Incidencias> findIncidenciasPendienteNotificacion(@Param("estadoIncidencia") Long estadoIncidencia);

    List<Incidencias> findByIdHabitacion(Long idHabitacion);
}
