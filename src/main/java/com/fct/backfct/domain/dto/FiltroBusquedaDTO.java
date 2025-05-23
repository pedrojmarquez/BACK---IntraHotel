package com.fct.backfct.domain.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroBusquedaDTO {
    private Integer capacidad;
    private String estado;
    private String numeroHabitacion;
    private String tipo;
    private String tipoBusqueda;
    private Integer planta;
}
