package com.fct.backfct.domain.models.dao;

import com.fct.backfct.domain.models.entity.LogsLimpieza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILogsLimpiezaDao extends JpaRepository<LogsLimpieza, Long> {
}
