package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadosIncidenciaDTO {
    private Long idEstadoIncidencia;
    private String nombre;
}
