package com.fct.backfct.domain.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "reservas_servicios")
public class ReservasServicios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_servicio")
    private Long idReservaServicio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva",referencedColumnName = "id_reserva")
    private Reservas reserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_servicio",referencedColumnName = "id_servicio")
    private Servicios servicio;

    private Integer cantidad;

    private Double precio;
}
