package com.fct.backfct.domain.dto;

import com.fct.backfct.domain.models.entity.Habitaciones;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogsLimpiezaDTO {

    private Long idLogLimpieza;
    private Long idHabitacion;
    private Integer limpiado;
    private LocalDateTime fechaLimpieza;
    private Long limpiadoPor;
}
