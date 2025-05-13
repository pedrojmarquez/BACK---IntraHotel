package com.fct.backfct.domain.services.Facturas;

import com.fct.backfct.domain.converters.FacturasMapper;
import com.fct.backfct.domain.converters.MetodosPagoMapper;
import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.models.dao.*;
import com.fct.backfct.domain.models.entity.*;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


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

    @Autowired
    private IHabitacionesDao habitacionesDao;

    @Autowired
    private IReservasDao reservasDao;

    @Autowired
    private IReservasServiciosDao reservasServiciosDao;


    @Override
    public String generarFacturaPDF(Facturas factura) throws IOException {
        //obtener el ultimo id de factura y sumarle 1
        Long idFactura = facturasDao.findUltimoId() + 1;
        factura.setIdFactura(idFactura);


        Context context = new Context();
        Clientes cliente = clientesDao.findById(factura.getCliente().getIdCliente()).orElse(null);

        context.setVariable("numeroFactura", factura.getIdFactura());

        context.setVariable("nombreCliente", cliente.getNombre() + " " + cliente.getApellidos());
        context.setVariable("emailCliente", cliente.getEmail());
        context.setVariable("telefonoCliente", cliente.getTelefono());
        context.setVariable("direccionCliente", cliente.getDireccion());

        Reservas reserva = reservasDao.findById(factura.getReserva().getIdReserva()).orElse(null);
        Habitaciones habitacion= habitacionesDao.findById(factura.getReserva().getHabitacion().getIdHabitacion()).orElse(null);

        context.setVariable("nombreHabitacion", habitacion.getTipo());
        context.setVariable("numeroNoches", calcularNumeroDeNoches(reserva.getFechaEntrada(), reserva.getFechaSalida()));
        context.setVariable("precioHabitacion", habitacion.getPrecioNoche());
        context.setVariable("totalHabitacion", habitacion.getPrecioNoche() * calcularNumeroDeNoches(reserva.getFechaEntrada(), reserva.getFechaSalida()));

        List<ReservasServicios> serviciosContratados = reservasServiciosDao.findByReserva_IdReserva(reserva.getIdReserva());

        context.setVariable("servicios", serviciosContratados);


        DecimalFormat df = new DecimalFormat("#.00");
        String iva = df.format(factura.getIva());

        context.setVariable("totalIVA", iva);
        context.setVariable("totalFactura", factura.getTotal());
        context.setVariable("metodoPago", factura.getMetodoPago().getNombre());
        context.setVariable("fechaPago", LocalDate.now().toString());

        String htmlContent = templateEngine.process("factura-template", context);

        String carpeta = "facturas/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue();
        Path directorio = Paths.get("src/main/resources/static/" + carpeta);
        Files.createDirectories(directorio);



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
        return metodosPagoDao.findMetodosPagoByNombreLike(nombre);
    }

    @Override
    public FacturasDTO save(FacturasDTO factura) {
        return facturasMapper.toDto(facturasDao.save(facturasMapper.toEntity(factura)));
    }

    @Override
    public List<MetodosPagoDTO> getMetodosPago() {
        return metodosPagoMapper.toListDtos(metodosPagoDao.findAll());
    }

    private Integer calcularNumeroDeNoches(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {

        //calcular el numero de noches entre las dos fechas contando que las noches se cuentan desde las 00:00
        return (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        }
}
