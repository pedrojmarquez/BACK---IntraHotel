package com.fct.backfct.domain.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "incidencias")
public class Incidencias implements Serializable {


    @Serial
    private static final long serialVersionUID = 2943678276296003575L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Long idIncidencia;

    @Column(name = "id_habitacion")
    private Long idHabitacion;

    @Column(name = "id_empleado_reporta")
    private Long idEmpleadoReporta;
    private String descripcion;

    @Column(name = "fecha_reporte")
    private Date fechaReporte;

    @Column(name = "id_estado_incidencia")
    private Long idEstadoIncidencia;

    @PrePersist
    public void prePersist() {
        this.fechaReporte = new Date();
    }

}
