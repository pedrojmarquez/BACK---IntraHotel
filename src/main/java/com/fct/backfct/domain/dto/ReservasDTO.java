package com.fct.backfct.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservasDTO {

    private Long idReserva;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaReserva;
    private Double precioTotal;
    private ClientesDTO cliente;
    private HabitacionesSinEstadoDTO habitacion;
    private EstadosReservaDTO estadosReserva;
}
