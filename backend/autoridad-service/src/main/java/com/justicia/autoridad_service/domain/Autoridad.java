package com.justicia.autoridad_service.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "autoridad")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "distrito_id", nullable = false)
    private UUID distritoId;

    public enum Tipo { JUEZ, FISCAL, DEFENSOR }
}
