package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tiene_audiencia_sala")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TieneAudienciaSala {

    @EmbeddedId
    private TieneAudienciaSalaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("audienciaId")
    @JoinColumn(name = "audiencia_id", columnDefinition = "UUID")
    private Audiencia audiencia;

    @Column(name = "sala_id", columnDefinition = "UUID", insertable = false, updatable = false)
    private UUID salaId;
}

