package com.fct.backfct.domain.services.Facturas;

import com.fct.backfct.domain.converters.FacturasMapper;
import com.fct.backfct.domain.converters.MetodosPagoMapper;
import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.models.dao.IClientesDao;
import com.fct.backfct.domain.models.dao.IFacturasDao;
import com.fct.backfct.domain.models.dao.IMetodosPagoDao;
import com.fct.backfct.domain.models.entity.Clientes;
import com.fct.backfct.domain.models.entity.Facturas;
import com.fct.backfct.domain.models.entity.MetodosPago;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;


@Service
public class FacturasServiceImpl implements IFacturasService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private IFacturasDao facturasDao;

    @Autowired
    private IMetodosPagoDao metodosPagoDao;

    @Autowired
    private MetodosPagoMapper metodosPagoMapper;

    @Autowired
    private FacturasMapper facturasMapper;

    @Autowired
    private IClientesDao clientesDao;


    @Override
    public String generarFacturaPDF(Facturas factura) throws IOException {
        Context context = new Context();
        Clientes cliente = clientesDao.findById(factura.getCliente().getIdCliente()).orElse(null);
        context.setVariable("nombreCliente", cliente.getNombre() + " " + cliente.getApellidos());
        context.setVariable("metodoPago", factura.getMetodoPago().getNombre());
        context.setVariable("subtotal", factura.getSubtotal());
        context.setVariable("total", factura.getTotal());
        context.setVariable("fecha", LocalDate.now().toString());

        String htmlContent = templateEngine.process("factura-template", context);

        String carpeta = "facturas/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue();
        Path directorio = Paths.get("src/main/resources/static/" + carpeta);
        Files.createDirectories(directorio);

        //obtener el ultimo id de factura y sumarle 1
        Long idFactura = facturasDao.findUltimoId() + 1;
        factura.setIdFactura(idFactura);

        String nombreArchivo = "factura_" + factura.getIdFactura() + ".pdf";
        Path rutaArchivo = directorio.resolve(nombreArchivo);

        try (OutputStream os = Files.newOutputStream(rutaArchivo)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        }

        return rutaArchivo.toString();
    }

    @Override
    public MetodosPago getMetodoPago(String nombre) {
        return metodosPagoDao.findMetodosPagoByNombreContainsIgnoreCase(nombre);
    }

    @Override
    public FacturasDTO save(FacturasDTO factura) {
        return facturasMapper.toDto(facturasDao.save(facturasMapper.toEntity(factura)));
    }

    @Override
    public List<MetodosPagoDTO> getMetodosPago() {
        return metodosPagoMapper.toListDtos(metodosPagoDao.findAll());
    }
}
