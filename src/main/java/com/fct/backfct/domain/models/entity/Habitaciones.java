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

    private Integer planta;

    private String tipo;

    @Column(name = "precio_noche")
    private Double precioNoche;

    private String descripcion;
    private Integer capacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado",referencedColumnName = "id_estado")
    private EstadosHabitacion estadoHabitacion;
}
