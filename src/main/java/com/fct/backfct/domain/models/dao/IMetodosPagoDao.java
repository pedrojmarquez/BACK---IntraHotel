package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMetodosPagoDao extends JpaRepository<MetodosPago, Long> {
    MetodosPago findMetodosPagoByNombreLike(String nombre);
}
