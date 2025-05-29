package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.converters.*;
import com.fct.backfct.domain.dto.*;
import com.fct.backfct.domain.models.dao.*;
import com.fct.backfct.domain.models.entity.*;
import com.fct.backfct.domain.services.EmailService;
import com.fct.backfct.domain.services.Facturas.FacturasServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservasServiceImpl implements IReservasService {

    @Autowired
    private IReservasDao reservasDao;

    @Autowired
    private IEstadoReservaDao estadoReservaDao;

    @Autowired
    private ReservasMapper reservasMapper;

    @Autowired
    private EstadosReservasMapper estadosMapper;

    @Autowired
    private IServiciosDao serviciosDao;

    @Autowired
    private ServiciosMapper serviciosMapper;

    @Autowired
    private FacturasServiceImpl facturasService;

    @Autowired
    private FacturasMapper facturasMapper;

    @Autowired
    private IReservasServiciosDao reservasServiciosDao;

    @Autowired
    private ReservaServicioMapper reservasServiciosMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IHabitacionesDao habitacionesDao;

    @Autowired
    private IEstadosHabitacionDao estadosHabitacionDao;

    @Override
    public List<ReservasDTO> findAll() {
        return reservasMapper.toListDtos(reservasDao.getReservasOrdenadasPorFecha());
    }

    @Override
    public List<EstadosReservaDTO> getEstadosReservas() {
        return estadosMapper.toListDtos(estadoReservaDao.findAll());
    }

    @Override
    public ReservasDTO update(ReservasDTO reservasDTO) {
        return reservasMapper.toDto(reservasDao.save(reservasMapper.toEntity(reservasDTO)));
    }

    @Override
    public ReservasDTO updateEstadosReservas(ReservasDTO reservasDTO) {
        Reservas reserva = reservasMapper.toEntity(reservasDTO);
        Habitaciones habitacion = reserva.getHabitacion();
        if(reserva.getEstadoReserva().getIdEstadoReserva() == 2L){
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(2L).orElse(null));
            habitacion.setLimpiezaDiaria(1);
            habitacionesDao.save(habitacion);
            log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a confirmada");
        }

        if(reserva.getEstadoReserva().getIdEstadoReserva() == 3L){
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(1L).orElse(null));
            habitacion.setLimpiezaDiaria(0);
            habitacionesDao.save(habitacion);
            log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a cancelada");
        }
        if (reserva.getEstadoReserva().getIdEstadoReserva() == 1L) {
            habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(1L).orElse(null));
            habitacion.setLimpiezaDiaria(0);
            habitacionesDao.save(habitacion);
            log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a pendiente");
        }
        if (reserva.getEstadoReserva().getIdEstadoReserva() == 4L) {
            if(habitacion.getEstadoHabitacion().getIdEstado() == 5L){
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
                habitacion.setLimpiezaDiaria(0);
                habitacionesDao.save(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a mantenimiento");
            }else{
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(3L).orElse(null));
                habitacion.setLimpiezaDiaria(0);
                habitacionesDao.save(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a limpieza");
            }
        }

        return reservasMapper.toDto(reservasDao.save(reserva));
    }

    @Override
    public ReservasDTO save(ReservasDTO reservasDTO) {
        return reservasMapper.toDto(reservasDao.save(reservasMapper.toEntity(reservasDTO)));
    }

    @Override
    public List<ServiciosDTO> getServiciosContratados(List<String> nombres) {
        return serviciosMapper.toListDtos(serviciosDao.findByNombres(nombres));
    }

    @Override
    public FacturasDTO checkout(ReservasDTO reservasDTO,String metodoPago) throws IOException {
        try {
            Reservas reserva = reservasMapper.toEntity(reservasDTO);

            //genero una factura para la reserva
            Facturas factura = new Facturas();
            factura.setReserva(reserva);
            factura.setCliente(reserva.getCliente());
            factura.setSubtotal(reserva.getPrecioTotal());
            factura.setIva(reserva.getPrecioTotal() - reserva.getPrecioTotal() / 1.10);
            factura.setTotal(factura.getSubtotal());
            factura.setMetodoPago(facturasService.getMetodoPago(metodoPago));

            factura.setRutaFichero(facturasService.generarFacturaPDF(factura));

            //obtener el nombre del fichero a partir de la ruta
            factura.setNombreFichero(factura.getRutaFichero().substring(factura.getRutaFichero().lastIndexOf("\\") + 1));
            FacturasDTO facturaDTO = facturasMapper.toDto(factura);


            reserva.setEstadoReserva(estadoReservaDao.findById(4L).orElse(null));

            Facturas facturaGuardada = facturasMapper.toEntity(facturasService.save(facturaDTO));
            reserva.setIdfactura(facturaGuardada.getIdFactura());

            reservasDao.save(reserva);
            log.info("Reserva " + reserva.getIdReserva() + " guardada con factura " + facturaGuardada.getIdFactura());
            Habitaciones habitacion = reserva.getHabitacion();

            //comprobamos que la la habitacion no tiene incidencias
            if(habitacion.getEstadoHabitacion().getIdEstado() == 5L){
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(4L).orElse(null));
                habitacion.setLimpiezaDiaria(0);
                habitacionesDao.save(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a mantenimiento");
            }else{
                habitacion.setEstadoHabitacion(estadosHabitacionDao.findById(3L).orElse(null));
                habitacion.setLimpiezaDiaria(0);
                habitacionesDao.save(habitacion);
                log.info("Habitacion " + habitacion.getIdHabitacion() + " cambia estado a limpieza");
            }




            enviarFactura(reserva.getCliente(),facturaGuardada.getRutaFichero());
            log.info("Factura enviada a " + reserva.getCliente().getEmail());

            return facturaDTO;
        }catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }

    public void enviarFactura(Clientes cliente, String rutaPdf) throws MessagingException {
        Context contexto = new Context();
        contexto.setVariable("nombre", cliente.getNombre());

        emailService.enviarFacturaConAdjunto(
                cliente.getEmail(),
                "Su Factura de IntraHotel",
                "factura-correo",  // sin .html
                contexto,
                rutaPdf
        );
    }

    @Override
    public List<ReservaServicioDTO> saveServiciosContratados(List<ReservaServicioDTO> serviciosContratadosDto) {
        List<ReservasServicios> serviciosContratados = reservasServiciosMapper.toListEntities(serviciosContratadosDto);
        return reservasServiciosMapper.toListDtos(reservasServiciosDao.saveAll(serviciosContratados));
    }

    @Override
    public List<ReservasDTO> findByHabitacionId(Long idHabitacion) {
        return reservasMapper.toListDtos(reservasDao.findTop5ByHabitacion_IdHabitacionOrderByFechaReservaDesc(idHabitacion));
    }

    @Override
    public List<ReservasDTO> findReservasByClienteId(Long id) {

        return reservasMapper.toListDtos(reservasDao.findTop3ByCliente_IdClienteOrderByFechaReservaDesc(id));
    }

    @Override
    public Optional<LocalDateTime> obtenerFechaLimiteParaAmpliar(Long habitacionId, LocalDateTime fechaSalidaActual) {
        List<Reservas> siguientes = reservasDao.findSiguientesReservas(habitacionId, fechaSalidaActual);
        if (!siguientes.isEmpty()) {
            return Optional.of(siguientes.get(0).getFechaEntrada());
        }
        return Optional.empty(); // No hay más reservas, puede ampliar libremente
    }

    @Override
    public List<ReservaServicioDTO> findServiciosByReservaId(Long idReserva) {
        return reservasServiciosMapper.toListDtos(reservasServiciosDao.findByReserva_IdReserva(idReserva));
    }

}
