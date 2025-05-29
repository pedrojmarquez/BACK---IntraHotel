package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.ImagenesIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagenesIncidenciaDao extends JpaRepository<ImagenesIncidencia, Long> {

    List<ImagenesIncidencia> findByIncidenciaIdIncidenciaAndTipo(Long idIncidencia, String tipo);

}
