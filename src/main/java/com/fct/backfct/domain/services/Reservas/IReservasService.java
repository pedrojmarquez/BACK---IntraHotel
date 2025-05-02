package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.dto.ReservasDTO;

import java.util.List;

public interface IReservasService {

    List<ReservasDTO> findAll();

    List<EstadosReservaDTO> getEstadosReservas();

    ReservasDTO update(ReservasDTO reservasDTO);

    ReservasDTO save(ReservasDTO reservasDTO);
}
