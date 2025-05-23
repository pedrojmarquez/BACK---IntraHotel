package com.fct.backfct.domain.services.Habitaciones;

import com.fct.backfct.domain.dto.FiltroBusquedaDTO;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.dto.IncidenciasDTO;
import com.fct.backfct.domain.models.entity.Habitaciones;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface IHabitacionesService {

    List<HabitacionesDTO> findAll();
    List<HabitacionesDTO> findByEstado(String estado);

    List<HabitacionesDTO> findAllDisponibles(LocalDateTime fechaEntrada, LocalDateTime fechaSalida, String tipo, Integer capacidad);

    List<HabitacionesDTO> findAllLimpieza(Integer planta);

    List<HabitacionesDTO> limpiar(List<HabitacionesDTO> habitacionesDTO);

    IncidenciasDTO crearIncidencia(HttpServletRequest request, Long idHabitacion, String observaciones, List<MultipartFile> imagenes);

    List<Habitaciones> buscarPorFiltrosHabitacion(FiltroBusquedaDTO filtro);

    List<Habitaciones> buscarPorFiltrosCliente(FiltroBusquedaDTO filtro);
    List<Habitaciones> buscarPorFiltrosReserva(FiltroBusquedaDTO filtro);
}
