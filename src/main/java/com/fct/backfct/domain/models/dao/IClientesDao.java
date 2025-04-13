package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientesDao extends JpaRepository<Clientes, Long> {
}
