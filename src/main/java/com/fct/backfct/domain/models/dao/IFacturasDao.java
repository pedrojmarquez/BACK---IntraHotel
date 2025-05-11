package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IFacturasDao extends JpaRepository<Facturas, Long> {
    @Query("SELECT MAX(f.idFactura) FROM Facturas f")
    Long findUltimoId();}
