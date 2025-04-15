package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturasDao extends JpaRepository<Facturas, Long> {
}
