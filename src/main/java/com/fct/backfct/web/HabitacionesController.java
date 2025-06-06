package com.fct.backfct.web;

import com.fct.backfct.domain.dto.FiltroBusquedaDTO;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.dto.ImagenesIncidenciaDTO;
import com.fct.backfct.domain.dto.LogsLimpiezaDTO;
import com.fct.backfct.domain.models.dao.IEmpleadosDao;
import com.fct.backfct.domain.models.dao.ILogsLimpiezaDao;
import com.fct.backfct.domain.models.entity.Habitaciones;
import com.fct.backfct.domain.models.entity.LogsLimpieza;
import com.fct.backfct.domain.services.Habitaciones.IHabitacionesService;
import com.fct.backfct.security.entity.Empleados;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(name = "Habitaciones", description = "Operaciones relacionadas con habitaciones, limpieza, mantenimiento e incidencias")
public class HabitacionesController {

    @Autowired
    private IHabitacionesService habitacionesService;

    @Autowired
    private ILogsLimpiezaDao logsLimpiezaDao;

    @Autowired
    private IEmpleadosDao empleadosDao;

    @Operation(summary = "Obtener habitaciones disponibles",
            description = "Devuelve una lista de habitaciones disponibles entre dos fechas opcionalmente filtradas por tipo y capacidad.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de habitaciones disponible",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionesDTO>> findAllDisponibles(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime fechaEntrada,
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

    @Operation(summary = "Obtener habitaciones para limpieza", description = "Devuelve habitaciones que requieren limpieza, opcionalmente filtradas por planta.")
    @GetMapping("/limpieza")
    public ResponseEntity<List<HabitacionesDTO>> findAllLimpieza(@RequestParam(required = false) Integer planta) {
        try {
            return ResponseEntity.ok(habitacionesService.findAllLimpieza(planta));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Marcar habitaciones como limpiadas", description = "Permite marcar una lista de habitaciones como limpiadas por el empleado autenticado.")
    @PostMapping("/limpiar")
    public ResponseEntity<List<HabitacionesDTO>> limpiar(HttpServletRequest request, @RequestBody List<HabitacionesDTO> habitacionesDTO) {
        try {
            return ResponseEntity.ok(habitacionesService.limpiar(request, habitacionesDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Registrar limpieza diaria", description = "Registra el log de limpieza diaria para una habitación específica.")
    @PostMapping("/limpieza-diaria/{idHabitacion}")
    public ResponseEntity<LogsLimpiezaDTO> limpiezaDiaria(HttpServletRequest request, @PathVariable Long idHabitacion, @RequestBody LogsLimpiezaDTO logsLimpiezaDTO) {
        try {
            return ResponseEntity.ok(habitacionesService.saveLogsLimpieza(request, logsLimpiezaDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Registrar incidencia", description = "Crea una nueva incidencia con observaciones e imágenes asociadas a una habitación.")
    @PostMapping("/{idHabitacion}/incidencias")
    public ResponseEntity<?> crearIncidencia(HttpServletRequest request,
                                             @PathVariable Long idHabitacion,
                                             @RequestParam("observaciones") String observaciones,
                                             @RequestParam("imagenes") List<MultipartFile> imagenes) {
        try {
            return ResponseEntity.ok(habitacionesService.crearIncidencia(request, idHabitacion, observaciones, imagenes));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Registrar mantenimiento", description = "Realiza mantenimiento sobre una incidencia con imágenes y observaciones.")
    @PostMapping("/{idHabitacion}/mantenimiento/{idIncidencia}")
    public ResponseEntity<?> hacerMantenimiento(HttpServletRequest request,
                                                @PathVariable Long idIncidencia,
                                                @PathVariable Long idHabitacion,
                                                @RequestParam("observaciones") String observaciones,
                                                @RequestParam("imagenes") List<MultipartFile> imagenes) {
        try {
            return ResponseEntity.ok(habitacionesService.hacerMantenimiento(request, idIncidencia, idHabitacion, observaciones, imagenes));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener habitaciones en mantenimiento")
    @GetMapping("/mantenimiento")
    public ResponseEntity<List<HabitacionesDTO>> findAllMantenimiento(@RequestParam(required = false) Integer planta) {
        try {
            return ResponseEntity.ok(habitacionesService.findAllParaMantenimiento(planta));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar habitaciones por filtros avanzados")
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

    @Operation(summary = "Obtener detalles de una habitación por ID")
    @GetMapping("/{idHabitacion}")
    public ResponseEntity<HabitacionesDTO> findById(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(habitacionesService.findById(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener imágenes de incidencias asociadas")
    @GetMapping("/{idHabitacion}/imagenes-incidencias")
    public ResponseEntity<List<ImagenesIncidenciaDTO>> getImagenesIncidencias(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(habitacionesService.getImagenesByHabitacion(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Visualizar imagen de prueba")
    @GetMapping("/ver-imagen")
    public ResponseEntity<Resource> verImagen() throws IOException {
        Path path = Paths.get("C:/Users/Pedro J/Desktop/PROYECTO/back-fct/uploads/incidencias/17/df6428db-9bad-47a9-a3b0-28cff4030b24_17483575678652771552756137641419.jpg");
        UrlResource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @Operation(summary = "Consultar quién realizó la última limpieza diaria")
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