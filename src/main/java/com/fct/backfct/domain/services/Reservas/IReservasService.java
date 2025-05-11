package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.dto.ServiciosDTO;

import java.io.IOException;
import java.util.List;

public interface IReservasService {

    List<ReservasDTO> findAll();

    List<EstadosReservaDTO> getEstadosReservas();

    ReservasDTO update(ReservasDTO reservasDTO);

    ReservasDTO save(ReservasDTO reservasDTO);

    List<ServiciosDTO> getServiciosContratados(List<String> nombres);

    FacturasDTO checkout(ReservasDTO reservasDTO, String metodoPago) throws IOException;
}
