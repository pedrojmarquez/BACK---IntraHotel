package com.fct.backfct.domain.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "facturas")
public class Facturas implements Serializable {


    @Serial
    private static final long serialVersionUID = 1560676354161124962L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva",referencedColumnName = "id_reserva")
    private Reservas reserva;

    @Column(name = "numero_factura")
    private String numeroFactura;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Europe/Madrid")
    @Column(name = "fecha_emision")
    private Date fechaEmision;

    private Double subtotal;
    private Double iva;
    private Double total;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo_pago",referencedColumnName = "id_metodo_pago")
    private MetodosPago metodoPago;

    @Column(name = "nombre_cliente")
    private String nombreCliente;


    @Column(name = "dni_cliente")
    private String dniCliente;


    @Column(name = "direccion_cliente")
    private String direccionCliente;


    @Column(name = "ruta_fichero")
    private String rutaFichero;


    @Column(name = "nombre_fichero")
    private String nombreFichero;

    @PrePersist
    public void prePersist() {
        this.fechaEmision = new Date();
    }
}
