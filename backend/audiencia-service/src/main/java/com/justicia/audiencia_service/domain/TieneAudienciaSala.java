package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.*;

@Entity
@Table(name = "tiene_audiencia_sala")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TieneAudienciaSala that = (TieneAudienciaSala) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

