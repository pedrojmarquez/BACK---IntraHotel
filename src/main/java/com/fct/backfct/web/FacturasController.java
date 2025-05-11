package com.fct.backfct.web;

import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.services.Facturas.IFacturasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("facturas")
@CrossOrigin(origins={"http://localhost:4200"})
public class FacturasController {

    @Autowired
    private IFacturasService facturasService;

    @GetMapping("/metodos_pago")
    public ResponseEntity<List<MetodosPagoDTO>> getMetodosPago() {
        try {
            return ResponseEntity.ok(facturasService.getMetodosPago());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
