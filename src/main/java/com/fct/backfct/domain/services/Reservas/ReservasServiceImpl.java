package com.fct.backfct.domain.services.Reservas;

import com.fct.backfct.domain.converters.EstadosReservasMapper;
import com.fct.backfct.domain.converters.FacturasMapper;
import com.fct.backfct.domain.converters.ReservasMapper;
import com.fct.backfct.domain.converters.ServiciosMapper;
import com.fct.backfct.domain.dto.EstadosReservaDTO;
import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.dto.ReservasDTO;
import com.fct.backfct.domain.dto.ServiciosDTO;
import com.fct.backfct.domain.models.dao.IEstadoReservaDao;
import com.fct.backfct.domain.models.dao.IReservasDao;
import com.fct.backfct.domain.models.dao.IServiciosDao;
import com.fct.backfct.domain.models.entity.Facturas;
import com.fct.backfct.domain.models.entity.Reservas;
import com.fct.backfct.domain.services.Facturas.FacturasServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

    @Override
    public List<ReservasDTO> findAll() {
        return reservasMapper.toListDtos(reservasDao.findAll());
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
            factura.setIva(reserva.getPrecioTotal() * 0.21);
            factura.setTotal(factura.getSubtotal() + factura.getIva());
            factura.setMetodoPago(facturasService.getMetodoPago(metodoPago));

            factura.setRutaFichero(facturasService.generarFacturaPDF(factura));

            //obtener el nombre del fichero a partir de la ruta
            factura.setNombreFichero(factura.getRutaFichero().substring(factura.getRutaFichero().lastIndexOf("\\") + 1));
            FacturasDTO facturaDTO = facturasMapper.toDto(factura);


            reserva.setEstadoReserva(estadoReservaDao.findById(4L).orElse(null));

            Facturas facturaGuardada = facturasMapper.toEntity(facturasService.save(facturaDTO));
            reserva.setIdfactura(facturaGuardada.getIdFactura());
            reservasDao.save(reserva);
            return facturaDTO;
        }catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }
}
