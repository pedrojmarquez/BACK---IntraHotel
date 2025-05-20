package com.fct.backfct.domain.services.Habitaciones;

import com.fct.backfct.domain.converters.EstadosIncidenciaMapper;
import com.fct.backfct.domain.converters.HabitacionesMapper;
import com.fct.backfct.domain.converters.ImagenesIncidenciaMapper;
import com.fct.backfct.domain.dto.EstadosIncidenciaDTO;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.dto.IncidenciasDTO;
import com.fct.backfct.domain.models.dao.*;
import com.fct.backfct.domain.models.entity.EstadosHabitacion;
import com.fct.backfct.domain.models.entity.Habitaciones;
import com.fct.backfct.domain.models.entity.ImagenesIncidencia;
import com.fct.backfct.domain.models.entity.Incidencias;
import com.fct.backfct.security.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class HabitacionesServiceImpl implements IHabitacionesService {

    @Autowired
    private IHabitacionesDao habitacionesDao;

    @Autowired
    private HabitacionesMapper habitacionesMapper;

    @Autowired
    private IEstadosHabitacionDao estadosHabitacionDao;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private IIncidenciasDao incidenciasDao;

    @Autowired
    private IImagenesIncidenciaDao imagenesIncidenciaDao;

    @Autowired
    private IEstadosIncidenciaDao estadosIncidenciaDao;

    @Autowired
    private EstadosIncidenciaMapper estadosIncidenciaMapper;

    @Autowired
    private ImagenesIncidenciaMapper imagenesIncidenciaMapper;





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

    @Override
    public List<HabitacionesDTO> findAllLimpieza(Integer planta) {
        List<Habitaciones> habitaciones =  habitacionesDao.findHabitacionesLimpieza(planta);
        return habitacionesMapper.toListDtos(habitaciones);
    }

    @Override
    public List<HabitacionesDTO> limpiar(List<HabitacionesDTO> habitacionesDTO) {
        List<Habitaciones> habitaciones =  habitacionesMapper.toListEntities(habitacionesDTO);
        List<Habitaciones> habitacionesLimpias = new java.util.ArrayList<>(List.of());
        for(Habitaciones habitacion : habitaciones) {
            if(habitacion.getEstadoHabitacion().getIdEstado() == 3L){
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(1L).orElse(null));
                habitacionesDao.save(habitacion);
                habitacionesLimpias.add(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " limpia");
            }else{
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
                habitacionesDao.save(habitacion);
                habitacionesLimpias.add(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " limpia, ahora esta en mantenimiento");
            }
        }
        return habitacionesMapper.toListDtos(habitacionesLimpias);
    }

    @Override
    public IncidenciasDTO crearIncidencia(HttpServletRequest request, Long idHabitacion, String observaciones, List<MultipartFile> imagenes) {
        // 1. Crear y guardar la incidencia
        Incidencias incidencia = new Incidencias();
        incidencia.setIdHabitacion(idHabitacion);
        incidencia.setIdEmpleadoReporta(jwtProvider.getUserFromToken(request).getId());
        incidencia.setDescripcion(observaciones);
        incidencia.setIdEstadoIncidencia(1L);
        incidencia = incidenciasDao.save(incidencia);

        Habitaciones habitacion = habitacionesDao.findById(idHabitacion).orElse(null);
        if(habitacion.getEstadoHabitacion().getIdEstado() == 3L){
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(5L).orElse(null));
            habitacionesDao.save(habitacion);
        }else{
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
            habitacionesDao.save(habitacion);
        }

        List<ImagenesIncidencia> imagenesIncidencia= new java.util.ArrayList<>(List.of());


        // 2. Guardar las imágenes relacionadas
        for (MultipartFile imagen : imagenes) {
            if (!imagen.isEmpty()) {
                String ruta = guardarImagen(imagen, idHabitacion); // Implementas esta función
                ImagenesIncidencia imagenEntidad = new ImagenesIncidencia();
                imagenEntidad.setIncidencia(incidencia);
                imagenEntidad.setRutaImagen(ruta);
                imagenesIncidencia.add(imagenesIncidenciaDao.save(imagenEntidad));
            }
        }

        IncidenciasDTO incidenciaDTO = new IncidenciasDTO();
        incidenciaDTO.setIdIncidencia(incidencia.getIdIncidencia());
        incidenciaDTO.setIdHabitacion(incidencia.getIdHabitacion());
        incidenciaDTO.setIdEmpleadoReporta(incidencia.getIdEmpleadoReporta());
        incidenciaDTO.setDescripcion(incidencia.getDescripcion());
        incidenciaDTO.setFechaReporte(incidencia.getFechaReporte());
        incidenciaDTO.setEstado(estadosIncidenciaMapper.toDto( estadosIncidenciaDao.findById(incidencia.getIdEstadoIncidencia()).orElse(null)));
        incidenciaDTO.setImagenes(imagenesIncidenciaMapper.toListDtos(imagenesIncidencia));

        return incidenciaDTO;
    }

    public String guardarImagen(MultipartFile archivo, Long idHabitacion) {
        try {
            String rutaBase = "src/main/resources/uploads/incidencias/" + idHabitacion;
            File dir = new File(rutaBase);
            if (!dir.exists()) dir.mkdirs();

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(rutaBase, nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
            return rutaBase + "/" + nombreArchivo;

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
