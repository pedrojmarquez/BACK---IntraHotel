package com.fct.backfct.domain.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String nombre;
    private String apellidos;
    private String telefono;
    private String dni;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Date fechaReserva;
    private Long estadoReserva;
}
