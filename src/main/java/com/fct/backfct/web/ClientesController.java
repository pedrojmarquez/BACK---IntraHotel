package com.fct.backfct.web;

import com.fct.backfct.domain.dto.ClientesDTO;
import com.fct.backfct.domain.services.Cliente.IClientesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("clientes")
@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class ClientesController {

    @Autowired
    private IClientesService clientesService;

    @Operation(summary = "Obtener todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("")
    public ResponseEntity<List<ClientesDTO>> findAll() {
        try {
            return ResponseEntity.ok(clientesService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<ClientesDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientesService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar información de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al actualizar el cliente")
    })
    @PutMapping("/update")
    public ResponseEntity<ClientesDTO> update(HttpServletRequest request, @RequestBody ClientesDTO clientesDTO) {
        try {
            return ResponseEntity.ok(clientesService.update(clientesDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Eliminar un cliente (borrado lógico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el cliente")
    })
    @PutMapping("/delete")
    public ResponseEntity<ClientesDTO> delete(HttpServletRequest request, @RequestBody ClientesDTO clientesDTO) {
        try {
            return ResponseEntity.ok(clientesService.delete(clientesDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Guardar un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente guardado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al guardar el cliente")
    })
    @PostMapping("/save")
    public ResponseEntity<ClientesDTO> save(HttpServletRequest request, @RequestBody ClientesDTO clientesDTO) {
        try {
            return ResponseEntity.ok(clientesService.save(clientesDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar clientes por ID de habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "500", description = "Error al buscar clientes")
    })
    @GetMapping("/habitacion/{idHabitacion}")
    public ResponseEntity<List<ClientesDTO>> findByHabitacion(@PathVariable Long idHabitacion) {
        try {
            return ResponseEntity.ok(clientesService.findByHabitacionId(idHabitacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar cliente por DNI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClientesDTO> findByDni(@PathVariable String dni) {
        try {
            return ResponseEntity.ok(clientesService.findByDni(dni));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
