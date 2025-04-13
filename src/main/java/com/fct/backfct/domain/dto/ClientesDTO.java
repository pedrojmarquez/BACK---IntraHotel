package com.fct.backfct.domain.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDTO {
    private Long idCliente;
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String direccion;
    private Date fechaRegistro;

}
