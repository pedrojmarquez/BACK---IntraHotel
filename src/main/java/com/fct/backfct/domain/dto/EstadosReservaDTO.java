package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadosReservaDTO {

    private Long idEstadoReserva;
    private String nombre;
}
