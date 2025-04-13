package com.fct.backfct.security.entity;

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
@Table(name = "roles")
public class Roles implements Serializable {

    @Serial
    private static final long serialVersionUID = -4328997024618730567L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(name = "nombre_rol")
    private String nombreRol;
    private String descripcion;
}