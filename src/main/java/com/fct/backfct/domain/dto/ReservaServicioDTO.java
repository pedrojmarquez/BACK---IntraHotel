package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaServicioDTO {

    private Long idReservaServicio;
    private ReservasDTO reserva;
    private ServiciosDTO servicio;
    private String nombreServicio;
    private Integer cantidad;
    private Double precio;
    private Double total;
}
