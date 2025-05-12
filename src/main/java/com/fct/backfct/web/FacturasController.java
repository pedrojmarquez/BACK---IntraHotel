package com.fct.backfct.web;

import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.models.dao.IFacturasDao;
import com.fct.backfct.domain.models.dao.IReservasDao;
import com.fct.backfct.domain.models.entity.Reservas;
import com.fct.backfct.domain.services.Facturas.IFacturasService;
import com.openhtmltopdf.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("facturas")
@CrossOrigin(origins={"http://localhost:4200"})
public class FacturasController {

    @Autowired
    private IFacturasService facturasService;

    @Autowired
    private IFacturasDao facturasDao;

    @Autowired
    private IReservasDao reservasDao;

    @GetMapping("/metodos_pago")
    public ResponseEntity<List<MetodosPagoDTO>> getMetodosPago() {
        try {
            return ResponseEntity.ok(facturasService.getMetodosPago());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/descargar")
    public ResponseEntity<ByteArrayResource> descargarFactura(@PathVariable Long id) throws IOException {

        Reservas reserva = reservasDao.findById(id).orElse(null);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }

        //ruta del pdf de la factura
        String rutaPdf = facturasDao.findById(reserva.getIdfactura()).orElse(null).getRutaFichero();
        Path path = Paths.get(rutaPdf);

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(path.toFile().length())
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_" + id + ".pdf")
                .body(resource);
    }

}
