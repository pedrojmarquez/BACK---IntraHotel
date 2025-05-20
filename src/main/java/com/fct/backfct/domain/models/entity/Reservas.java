package com.fct.backfct.domain.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "reservas")
public class Reservas implements Serializable {


    @Serial
    private static final long serialVersionUID = -9107514159402565450L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente",referencedColumnName = "id_cliente")
    private Clientes cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_habitacion",referencedColumnName = "id_habitacion")
    private Habitaciones habitacion;

    @Column(name = "fecha_entrada")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Europe/Madrid")
    private LocalDateTime fechaEntrada;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Europe/Madrid")
    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(name = "precio_total")
    private Double precioTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_reserva",referencedColumnName = "id_estado_reserva")
    private EstadosReserva estadoReserva;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm",timezone = "Europe/Madrid")
    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;


    @Column(name = "id_factura")
    private Long Idfactura;

    @PrePersist
    public void prePersist() {
        this.fechaReserva = LocalDateTime.now();
    }




}
