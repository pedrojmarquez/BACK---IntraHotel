package com.fct.backfct.domain.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "logs_limpieza")
public class LogsLimpieza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log_limpieza")
    private Long idLogLimpieza;

@Column(name = "id_habitacion")
    private Long idHabitacion;

    private Integer limpiado;

    @Column(name = "fecha_limpieza")
    private LocalDateTime fechaLimpieza;

    @Column(name = "limpiado_por")
    private Long LimpiadoPor;

    @PrePersist
    public void prePersist() {
        this.fechaLimpieza = LocalDateTime.now();
    }
}
