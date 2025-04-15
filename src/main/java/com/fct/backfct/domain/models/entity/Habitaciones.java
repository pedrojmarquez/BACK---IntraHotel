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
@Table(name = "habitaciones")
public class Habitaciones implements Serializable {


    @Serial
    private static final long serialVersionUID = -6056335386080674445L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Long idHabitacion;

    @Column(name = "numero_habitacion")
    private String numeroHabitacion;

    private String tipo;

    @Column(name = "precio_noche")
    private Double precioNoche;

    private String descripcion;
    private Integer capacidad;

    @Column(name = "id_estado")
    private Long idEstado;
}
