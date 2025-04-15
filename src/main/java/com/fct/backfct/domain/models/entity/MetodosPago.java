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
@Table(name = "metodos_pago")
public class MetodosPago implements Serializable {


    @Serial
    private static final long serialVersionUID = 6318394270215410632L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private Long idMetodoPago;
    private String nombre;
    private String descripcion;

}
