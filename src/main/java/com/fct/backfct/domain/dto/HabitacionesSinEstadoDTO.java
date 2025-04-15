package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionesSinEstadoDTO {
    private Long idHabitacion;
    private String numeroHabitacion;
    private String tipo;
    private Double precioNoche;
    private String descripcion;
    private Integer capacidad;

}
