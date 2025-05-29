package com.fct.backfct.web;

import com.fct.backfct.domain.dto.FiltroBusquedaDTO;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.dto.ImagenesIncidenciaDTO;
import com.fct.backfct.domain.dto.LogsLimpiezaDTO;
import com.fct.backfct.domain.models.dao.IEmpleadosDao;
import com.fct.backfct.domain.models.dao.ILogsLimpiezaDao;
import com.fct.backfct.domain.models.entity.Habitaciones;
import com.fct.backfct.domain.models.entity.Incidencias;
import com.fct.backfct.domain.models.entity.LogsLimpieza;
import com.fct.backfct.domain.services.Habitaciones.IHabitacionesService;
import com.fct.backfct.security.entity.Empleados;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("habitaciones")
@CrossOrigin(origins={"http://localhost:4200"})
public class HabitacionesController {

    @Autowired
    private IHabitacionesService habitacionesService;

    @Autowired
    private ILogsLimpiezaDao logsLimpiezaDao;

    @Autowired
    private IEmpleadosDao empleadosDao;

    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionesDTO>> findAllDisponibles(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaEntrada,
                                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaSalida,
                                                                    @RequestParam(required = false) String tipo,
                                                                    @RequestParam(required = false) Integer capacidad) {
        try {
            return ResponseEntity.ok(habitacionesService.findAllDisponibles(fechaEntrada, fechaSalida, tipo, capacidad));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/limpieza")
    public ResponseEntity<List<HabitacionesDTO>> findAllLimpieza(@RequestParam(required = false) Integer planta) {
        try {
            return ResponseEntity.ok(habitacionesService.findAllLimpieza(planta));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/limpiar")
    public ResponseEntity<List<HabitacionesDTO>> limpiar(HttpServletRequest request,@RequestBody List<HabitacionesDTO> habitacionesDTO) {
        try {
            return ResponseEntity.ok(habitacionesService.limpiar(request,habitacionesDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/limpieza-diaria/{idHabitacion}")
    public ResponseEntity<LogsLimpiezaDTO> limpiezaDiaria(HttpServletRequest request,@PathVariable Long idHabitacion , @RequestBody LogsLimpiezaDTO logsLimpiezaDTO) {
        try {
            return ResponseEntity.ok(habitacionesService.saveLogsLimpieza(request,logsLimpiezaDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idHabitacion}/incidencias")
    public ResponseEntity<?> crearIncidencia(
            HttpServletRequest request,
            @PathVariable Long idHabitacion,
            @RequestParam("observaciones") String observaciones,
            @RequestParam("imagenes") List<MultipartFile> imagenes
    ) {
        try {
            return ResponseEntity.ok(habitacionesService.crearIncidencia(request, idHabitacion, observaciones, imagenes));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{idHabitacion}/mantenimiento/{idIncidencia}")
    public ResponseEntity<?> hacerMantenimiento(
            HttpServletRequest request,
            @PathVariable Long idIncidencia,
            @PathVariable Long idHabitacion,
            @RequestParam("observaciones") String observaciones,
            @RequestParam("imagenes") List<MultipartFile> imagenes
    ) {
        try {
            return ResponseEntity.ok(habitacionesService.hacerMantenimiento(request, idIncidencia, idHabitacion, observaciones, imagenes));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/mantenimiento")
    public ResponseEntity<List<HabitacionesDTO>> findAllMantenimiento(@RequestParam(required = false) Integer planta) {
        try {
            return ResponseEntity.ok(habitacionesService.findAllParaMantenimiento(planta));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/buscar")
    public ResponseEntity<?> buscarHabitaciones(@RequestBody FiltroBusquedaDTO filtro) {
        List<Habitaciones> resultados;

        switch (filtro.getTipoBusqueda()) {
            case "habitacion":
                resultados = habitacionesService.buscarPorFiltrosHabitacion(filtro);
                break;
            case "cliente":
                resultados = habitacionesService.buscarPorFiltrosCliente(filtro);
                break;
            case "reserva":
                resultados = habitacionesService.buscarPorFiltrosReserva(filtro);
                break;
            default:
                return ResponseEntity.badRequest().body("Tipo de búsqueda no válido");
        }

        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/{idHabitacion}")
    public ResponseEntity<HabitacionesDTO> findById(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(habitacionesService.findById(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{idHabitacion}/imagenes-incidencias")
    public ResponseEntity<List<ImagenesIncidenciaDTO>> getImagenesIncidencias(@PathVariable Long idHabitacion) {
        try{
            return ResponseEntity.ok(habitacionesService.getImagenesByHabitacion(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ver-imagen")
    public ResponseEntity<Resource> verImagen() throws IOException {
        Path path = Paths.get("C:/Users/Pedro J/Desktop/PROYECTO/back-fct/uploads/incidencias/17/df6428db-9bad-47a9-a3b0-28cff4030b24_17483575678652771552756137641419.jpg");
        UrlResource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/limpieza-diaria/{idHabitacion}/limpiado-por")
    public ResponseEntity<?> limpiezaDiariaPor(@PathVariable Long idHabitacion) {
        try {
            LogsLimpieza logsLimpieza = logsLimpiezaDao.findTop1ByIdHabitacionOrderByFechaLimpiezaDesc(idHabitacion);
            Empleados empleado = empleadosDao.findById(logsLimpieza.getLimpiadoPor()).orElse(null);
            Map<String, String> response = new HashMap<>();
            response.put("nombre", empleado.getNombre() + " " + empleado.getApellidos());
            response.put("fecha", logsLimpieza.getFechaLimpieza().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
