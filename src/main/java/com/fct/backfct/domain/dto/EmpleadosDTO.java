package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadosDTO {

    private Long id;
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
}
