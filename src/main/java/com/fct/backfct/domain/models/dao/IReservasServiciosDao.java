package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.ReservasServicios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservasServiciosDao extends JpaRepository<ReservasServicios, Long> {

    List<ReservasServicios> findByReserva_IdReserva(Long idReserva);
}
