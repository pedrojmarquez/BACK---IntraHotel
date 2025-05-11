package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServiciosDao extends JpaRepository<Servicios,Long> {


    @Query("SELECT s FROM Servicios s WHERE s.nombre IN :nombres")
    List<Servicios> findByNombres(@Param("nombres") List<String> nombres);
}
