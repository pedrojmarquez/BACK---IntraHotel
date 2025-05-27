package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.dto.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservasService {

    List<ReservasDTO> findAll();

    List<EstadosReservaDTO> getEstadosReservas();

    ReservasDTO update(ReservasDTO reservasDTO);

    ReservasDTO updateEstadosReservas(ReservasDTO reservasDTO);

    ReservasDTO save(ReservasDTO reservasDTO);

    List<ServiciosDTO> getServiciosContratados(List<String> nombres);

    FacturasDTO checkout(ReservasDTO reservasDTO, String metodoPago) throws IOException;

    List<ReservaServicioDTO> saveServiciosContratados(List<ReservaServicioDTO> serviciosContratadosDto);

    List<ReservasDTO> findByHabitacionId(Long idHabitacion);

    List<ReservasDTO> findReservasByClienteId(Long id);

    Optional<LocalDateTime> obtenerFechaLimiteParaAmpliar(Long habitacionId, LocalDateTime fechaSalidaActual);

    List<ReservaServicioDTO> findServiciosByReservaId(Long idReserva);
}
