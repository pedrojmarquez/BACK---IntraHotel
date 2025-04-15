package com.fct.backfct.domain.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservasDTO {

    private Long idReserva;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Date fechaReserva;
    private Double precioTotal;
    private ClientesDTO cliente;
    private HabitacionesSinEstadoDTO habitacion;
    private EstadosReservaDTO estadosReserva;
}
