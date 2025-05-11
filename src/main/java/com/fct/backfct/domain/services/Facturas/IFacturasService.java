package com.fct.backfct.domain.services.Facturas;

import com.fct.backfct.domain.dto.FacturasDTO;
import com.fct.backfct.domain.dto.MetodosPagoDTO;
import com.fct.backfct.domain.models.entity.Facturas;
import com.fct.backfct.domain.models.entity.MetodosPago;

import java.io.IOException;
import java.util.List;

public interface IFacturasService {

    String generarFacturaPDF(Facturas factura) throws IOException;

    MetodosPago getMetodoPago(String nombre);

    FacturasDTO save(FacturasDTO factura);

    List<MetodosPagoDTO> getMetodosPago();
}
