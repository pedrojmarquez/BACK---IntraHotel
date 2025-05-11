package com.fct.backfct.domain.dto;

import com.fct.backfct.domain.models.entity.Clientes;
import com.fct.backfct.domain.models.entity.MetodosPago;
import com.fct.backfct.domain.models.entity.Reservas;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacturasDTO {
    private Integer idFactura;
    private ReservasDTO reserva;
    private ClientesDTO cliente;
    private Double subtotal;
    private Double iva;
    private Double total;
    private MetodosPagoDTO metodoPago;
    private String rutaFichero;
    private String nombreFichero;
}
