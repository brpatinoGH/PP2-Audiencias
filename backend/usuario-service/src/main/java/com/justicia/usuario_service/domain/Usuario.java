package com.justicia.usuario_service.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "nombre_usuario", length = 255)
    private String nombreUsuario;

    @Column(name = "mail", length = 255)
    private String mail;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

}
