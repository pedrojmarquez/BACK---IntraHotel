package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiciosDTO {

    private Long idServicio;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;

}
