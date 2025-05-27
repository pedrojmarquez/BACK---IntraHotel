package com.fct.backfct.domain.models.entity;

import com.fct.backfct.security.entity.Empleados;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "limpiado_por",referencedColumnName = "id")
    private Empleados limpiadoPor;

    @Column(name = "limpiado")
    private LocalDateTime limpiado;

    @ManyToOne
    @JoinColumn(name = "mantenido_por",referencedColumnName = "id")
    private Empleados mantenidoPor;

    @Column(name = "mantenido")
    private LocalDateTime mantenido;

    @Column(name = "limpieza_diaria")
    private Integer limpiezaDiaria;


}
