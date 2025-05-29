package com.fct.backfct.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionesSinEstadoDTO {
    private Long idHabitacion;
    private String numeroHabitacion;
    private Integer planta;
    private String tipo;
    private Double precioNoche;
    private String descripcion;
    private Integer capacidad;
    private EmpleadosDTO limpiadoPor;
    private LocalDateTime limpiado;
    private EmpleadosDTO mantenidoPor;
    private LocalDateTime mantenido;
    private Integer limpiezaDiaria;

}
