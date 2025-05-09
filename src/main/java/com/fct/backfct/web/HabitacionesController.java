package com.fct.backfct.web;

import com.fct.backfct.domain.dto.HabitacionesDTO;
import com.fct.backfct.domain.services.Habitaciones.IHabitacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
