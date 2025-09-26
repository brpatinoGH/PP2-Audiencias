package com.justicia.autoridad_service.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "autoridad")
public class Autoridad extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "mail", length = 255)
    private String mail;

    @Column(name = "estado", length = 50)
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20)
    private Tipo tipo;

    public enum Tipo { JUEZ, FISCAL, DEFENSOR }
}
