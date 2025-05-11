package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetodosPagoDTO {

    private Integer idMetodoPago;
    private String nombre;
}
