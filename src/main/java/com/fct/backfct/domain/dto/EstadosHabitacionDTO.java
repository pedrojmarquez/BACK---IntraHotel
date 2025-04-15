package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadosHabitacionDTO {

    private Integer idEstadoHabitacion;
    private String nombre;
}
