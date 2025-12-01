package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "audiencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audiencia extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "cuit", length = 20)
    private String cuit;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Column(name = "creado_por_usuario_id", columnDefinition = "UUID")
    private UUID creadoPorUsuarioId;

    @Column(name = "modificado_por_usuario_id", columnDefinition = "UUID")
    private UUID modificadoPorUsuarioId;

    @Column(name = "distrito_id", columnDefinition = "UUID", nullable = false)
    private UUID distritoId;

    @OneToMany(mappedBy = "audiencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioAudienciaAccion> acciones = new HashSet<>();

    @OneToMany(mappedBy = "audiencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TieneAudienciaSala> audienciaSalas = new HashSet<>();

    @OneToMany(mappedBy = "audiencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TieneAudienciaAutoridad> audienciaAutoridades = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audiencia audiencia = (Audiencia) o;
        return getId() != null && Objects.equals(getId(), audiencia.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
