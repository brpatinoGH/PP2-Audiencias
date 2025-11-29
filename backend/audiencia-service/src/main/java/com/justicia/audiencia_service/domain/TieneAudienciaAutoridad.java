package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tiene_audiencia_autoridad")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TieneAudienciaAutoridad that = (TieneAudienciaAutoridad) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

