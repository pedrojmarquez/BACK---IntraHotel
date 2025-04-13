package com.fct.backfct.security.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NuevoUsuario {
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private Long idRol;
    private String clave;
    private Date fechaContratacion;
    private Boolean activo;
}