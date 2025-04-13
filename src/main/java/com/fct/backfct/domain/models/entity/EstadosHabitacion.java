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
@Table(name = "estados_habitacion")
public class EstadosHabitacion implements Serializable {


    @Serial
    private static final long serialVersionUID = 5949113505419677045L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long idEstado;

    private String nombre;
    private String descripcion;
}
