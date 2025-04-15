package com.fct.backfct.domain.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "estados_incidencia")
public class EstadosIncidencia implements Serializable {


    @Serial
    private static final long serialVersionUID = -4991700853380202618L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_incidencia")
    private Long idEstadoIncidencia;
    private String nombre;
    private String descripcion;
}
