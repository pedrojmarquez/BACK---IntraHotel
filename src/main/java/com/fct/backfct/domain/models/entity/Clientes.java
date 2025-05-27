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
@Table(name = "clientes")
public class Clientes implements Serializable {


    @Serial
    private static final long serialVersionUID = 2601794443291224779L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    private String dni;

    private String nombre;

    private String apellidos;

    private String email;

    private String telefono;

    private String direccion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Europe/Madrid")
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    private boolean activo = true;


    @PrePersist
    public void prePersist() {
        fechaRegistro = new Date();
        activo = true;
    }

}
