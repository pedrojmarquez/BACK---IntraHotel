package com.fct.backfct.domain.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciasDTO {

    private Long idIncidencia;
    private Long idHabitacion;
    private Long idEmpleadoReporta;
    private String descripcion;
    private Date fechaReporte;
    private EstadosIncidenciaDTO estado;
    private String descripcionArreglo;
    List<ImagenesIncidenciaDTO> imagenes;
}
