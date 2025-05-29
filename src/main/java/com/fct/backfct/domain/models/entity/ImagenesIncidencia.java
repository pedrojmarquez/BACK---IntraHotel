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
@Table(name = "imagenes_incidencia")
public class ImagenesIncidencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_incidencia",referencedColumnName = "id_incidencia")
    private Incidencias incidencia;

    private String tipo;

    @Column(name = "ruta_imagen")
    private String rutaImagen;
}
