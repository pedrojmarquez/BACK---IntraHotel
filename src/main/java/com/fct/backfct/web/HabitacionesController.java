package com.fct.backfct.web;

import com.fct.backfct.domain.dto.FiltroBusquedaDTO;
import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.models.entity.Habitaciones;
import com.fct.backfct.domain.models.entity.Incidencias;
import com.fct.backfct.domain.services.Habitaciones.IHabitacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("habitaciones")
@CrossOrigin(origins={"http://localhost:4200"})
public class HabitacionesController {

    @Autowired
    private IHabitacionesService habitacionesService;

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
    public ResponseEntity<List<HabitacionesDTO>> limpiar(@RequestBody List<HabitacionesDTO> habitacionesDTO) {
        try {
            return ResponseEntity.ok(habitacionesService.limpiar(habitacionesDTO));
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

}
