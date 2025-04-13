package com.fct.backfct.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "empleados")
public class Empleados implements Serializable {

    @Serial
    private static final long serialVersionUID = 6849108088398687980L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol",referencedColumnName = "id_rol")
    private Roles rol;

    private String clave;
    @Column(name = "fecha_contratacion")
    private Date fechaContratacion ;

    private Boolean activo;



    @PrePersist
    public void prePersist (){
        fechaContratacion = new Date();
        activo=true;
    }

    public Empleados(String dni, String nombre, String apellidos, String email, String telefono, Roles rol, String clave, Date fechaContratacion, Boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.clave = clave;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }
}
