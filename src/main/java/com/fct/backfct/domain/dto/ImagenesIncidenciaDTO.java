package com.fct.backfct.domain.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenesIncidenciaDTO {
    private Long idImagen;
    private IncidenciasDTO incidencia;
    private String tipo;
    private String rutaImagen;
}
