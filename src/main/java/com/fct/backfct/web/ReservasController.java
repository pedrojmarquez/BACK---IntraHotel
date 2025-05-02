package com.fct.backfct.web;

import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.dto.ReservasDTO;
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
}
