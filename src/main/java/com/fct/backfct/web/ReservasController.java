package com.fct.backfct.web;

import com.fct.backfct.domain.dto.*;
import com.fct.backfct.domain.services.Reservas.IReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
