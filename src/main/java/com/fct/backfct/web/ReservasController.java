package com.fct.backfct.web;

import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.services.Reservas.IReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
