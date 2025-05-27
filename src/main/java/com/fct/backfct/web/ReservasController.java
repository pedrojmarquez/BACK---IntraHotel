package com.fct.backfct.web;

import com.fct.backfct.domain.dto.*;
import com.fct.backfct.domain.services.Reservas.IReservasService;
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
@CrossOrigin(origins={"http://localhost:4200"})
public class ReservasController {

    @Autowired
    private IReservasService reservasService;

    @GetMapping("")
    public ResponseEntity<List<ReservasDTO>> findAll() {
        try {
            return ResponseEntity.ok(reservasService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/estados")
    public ResponseEntity<List<EstadosReservaDTO>> findEstadosReservas(){
        try {
            return ResponseEntity.ok(reservasService.getEstadosReservas());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ReservasDTO> update(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.update(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateEstado")
    public ResponseEntity<ReservasDTO> updateEstado(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.updateEstadosReservas(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ReservasDTO> save(@RequestBody ReservasDTO reservasDTO) {
        try {
            return ResponseEntity.ok(reservasService.save(reservasDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/servicios/contratados")
    public ResponseEntity<List<ServiciosDTO>> getServiciosContratados(@RequestParam(required = false) List<String> nombres) {
        try {
            return ResponseEntity.ok(reservasService.getServiciosContratados(nombres));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/checkout")
    public ResponseEntity<FacturasDTO> checkout(@RequestBody ReservasDTO reservasDTO, @RequestParam String metodoPago) {
        try {
            return ResponseEntity.ok(reservasService.checkout(reservasDTO, metodoPago));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/servicios-contratados/save")
    public ResponseEntity<List<ReservaServicioDTO>> saveServiciosContratados(@RequestBody List<ReservaServicioDTO> serviciosContratados) {
        try {
            return ResponseEntity.ok(reservasService.saveServiciosContratados(serviciosContratados));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/habitacion/{idHabitacion}")
    public ResponseEntity<List<ReservasDTO>> findByHabitacion(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(reservasService.findByHabitacionId(idHabitacion));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ReservasDTO>> findReservasByClienteId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservasService.findReservasByClienteId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @GetMapping("/habitaciones/{idHabitacion}/fechaMaximaAmpliacion")
    public ResponseEntity<Optional<LocalDateTime>> findFechaMaximaAmpliacion(@PathVariable Long idHabitacion,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fechaSalidaActual){
        try {
            return ResponseEntity.ok(reservasService.obtenerFechaLimiteParaAmpliar(idHabitacion, fechaSalidaActual));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{idReserva}/servicios")
    public ResponseEntity<List<ReservaServicioDTO>> findServiciosByReservaId(@PathVariable Long idReserva) {
        try {
            return ResponseEntity.ok(reservasService.findServiciosByReservaId(idReserva));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
