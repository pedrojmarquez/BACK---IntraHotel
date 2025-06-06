package com.fct.backfct.web;

import com.fct.backfct.domain.dto.*;
import com.fct.backfct.domain.services.Reservas.IReservasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reservas")
@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(name = "Reservas", description = "Operaciones relacionadas con reservas, servicios contratados y facturación")
public class ReservasController {

    @Autowired
    private IReservasService reservasService;

    @Operation(summary = "Obtener todas las reservas")
    @ApiResponse(responseCode = "200", description = "Listado de reservas recuperado con éxito")
    @GetMapping("")
    public ResponseEntity<List<ReservasDTO>> findAll() {
        try {
            return ResponseEntity.ok(reservasService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener todos los estados de reserva")
    @GetMapping("/estados")
    public ResponseEntity<List<EstadosReservaDTO>> findEstadosReservas() {
        try {
            return ResponseEntity.ok(reservasService.getEstadosReservas());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar una reserva")
    @PutMapping("/update")
    public ResponseEntity<ReservasDTO> update(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.update(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar el estado de una reserva")
    @PutMapping("/updateEstado")
    public ResponseEntity<ReservasDTO> updateEstado(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.updateEstadosReservas(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Guardar una nueva reserva")
    @PostMapping("/save")
    public ResponseEntity<ReservasDTO> save(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.save(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener servicios contratados")
    @GetMapping("/servicios/contratados")
    public ResponseEntity<List<ServiciosDTO>> getServiciosContratados(@RequestParam(required = false) List<String> nombres) {
        try {
            return ResponseEntity.ok(reservasService.getServiciosContratados(nombres));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Realizar el checkout de una reserva y generar factura")
    @PostMapping("/checkout")
    public ResponseEntity<FacturasDTO> checkout(@RequestBody ReservasDTO reservasDTO, @RequestParam String metodoPago) {
        try {
            return ResponseEntity.ok(reservasService.checkout(reservasDTO, metodoPago));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Guardar servicios contratados para una reserva")
    @PostMapping("/servicios-contratados/save")
    public ResponseEntity<List<ReservaServicioDTO>> saveServiciosContratados(@RequestBody List<ReservaServicioDTO> serviciosContratados) {
        try {
            return ResponseEntity.ok(reservasService.saveServiciosContratados(serviciosContratados));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener reservas por ID de habitación")
    @GetMapping("/habitacion/{idHabitacion}")
    public ResponseEntity<List<ReservasDTO>> findByHabitacion(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(reservasService.findByHabitacionId(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener reservas por ID de cliente")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ReservasDTO>> findReservasByClienteId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservasService.findReservasByClienteId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener fecha máxima de ampliación de reserva")
    @GetMapping("/habitaciones/{idHabitacion}/fechaMaximaAmpliacion")
    public ResponseEntity<Optional<LocalDateTime>> findFechaMaximaAmpliacion(
            @PathVariable Long idHabitacion,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fechaSalidaActual) {
        try {
            return ResponseEntity.ok(reservasService.obtenerFechaLimiteParaAmpliar(idHabitacion, fechaSalidaActual));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener servicios contratados por ID de reserva")
    @GetMapping("/{idReserva}/servicios")
    public ResponseEntity<List<ReservaServicioDTO>> findServiciosByReservaId(@PathVariable Long idReserva) {
        try {
            return ResponseEntity.ok(reservasService.findServiciosByReservaId(idReserva));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar lista de servicios contratados")
    @PutMapping("/servicios-contratados/update")
    public ResponseEntity<List<ReservaServicioDTO>> updateServiciosContratados(@RequestBody List<ReservaServicioDTO> serviciosContratados) {
        try {
            return ResponseEntity.ok(reservasService.updateServiciosContratados(serviciosContratados));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener estadísticas generales de reservas")
    @GetMapping("/estadisticas")
    public ResponseEntity<?> getEstadisticas() {
        try {
            return ResponseEntity.ok(reservasService.getEstadisticas());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
