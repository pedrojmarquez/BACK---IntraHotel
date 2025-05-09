package com.fct.backfct.domain.services.Habitaciones;

import com.fct.backfct.domain.dto.HabitacionesDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IHabitacionesService {

    List<HabitacionesDTO> findAll();
    List<HabitacionesDTO> findByEstado(String estado);

    List<HabitacionesDTO> findAllDisponibles(LocalDateTime fechaEntrada, LocalDateTime fechaSalida, String tipo, Integer capacidad);

}
