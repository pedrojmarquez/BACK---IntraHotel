package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionesDTO {

    private Long idHabitacion;
    private String numeroHabitacion;
    private Integer planta;
    private String tipo;
    private Double precioNoche;
    private String descripcion;
    private Integer capacidad;
    private EstadosHabitacionDTO estadoHabitacion;
}
