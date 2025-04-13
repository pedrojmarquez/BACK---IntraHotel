package com.fct.backfct.web;

import com.fct.backfct.domain.dto.ClientesDTO;
import com.fct.backfct.domain.models.entity.Clientes;
import com.fct.backfct.domain.services.IClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("clientes")
@CrossOrigin(origins={"http://localhost:4200"})
public class ClientesController {

    @Autowired
    private IClientesService clientesService;

    @GetMapping("")
    public ResponseEntity<List<ClientesDTO>> findAll() {
        try {
            return ResponseEntity.ok(clientesService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientesDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientesService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @PutMapping("/update")
    public ResponseEntity<ClientesDTO> update(HttpServletRequest request,@RequestBody ClientesDTO clientesDTO) {
        try {
            return ResponseEntity.ok(clientesService.update(clientesDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }

    @PostMapping("/save")
    public ResponseEntity<ClientesDTO> save(HttpServletRequest request,@RequestBody ClientesDTO clientesDTO) {
        try {
            return ResponseEntity.ok(clientesService.save(clientesDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
    }
}
