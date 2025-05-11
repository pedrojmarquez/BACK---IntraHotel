package com.fct.backfct.domain.services.Habitaciones;

import com.fct.backfct.domain.converters.HabitacionesMapper;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.models.dao.IHabitacionesDao;
import com.fct.backfct.domain.models.entity.Habitaciones;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class HabitacionesServiceImpl implements IHabitacionesService {

    @Autowired
    private IHabitacionesDao habitacionesDao;

    @Autowired
    private HabitacionesMapper habitacionesMapper;

    @Override
    public List<HabitacionesDTO> findAll() {
        return List.of();
    }

    @Override
    public List<HabitacionesDTO> findByEstado(String estado) {
        return List.of();
    }

    @Override
    public List<HabitacionesDTO> findAllDisponibles(LocalDateTime fechaEntrada, LocalDateTime fechaSalida, String tipo, Integer capacidad) {
        List<Habitaciones> habitaciones =  habitacionesDao.findHabitacionesDisponibles(fechaEntrada, fechaSalida, tipo, capacidad);
        return habitacionesMapper.toListDtos(habitaciones);
    }
}
