package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tiene_audiencia_autoridad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TieneAudienciaAutoridad {

    @EmbeddedId
    private TieneAudienciaAutoridadId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("audienciaId")
    @JoinColumn(name = "audiencia_id", columnDefinition = "UUID")
    private Audiencia audiencia;

    @Column(name = "autoridad_id", columnDefinition = "UUID", insertable = false, updatable = false)
    private UUID autoridadId;
}

