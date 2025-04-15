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
@Table(name = "estados_reserva")
public class EstadosReserva implements Serializable {


    @Serial
    private static final long serialVersionUID = 6259662672474364748L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_reserva")
    private Long idEstadoReserva;
    private String nombre;
    private String descripcion;

}
