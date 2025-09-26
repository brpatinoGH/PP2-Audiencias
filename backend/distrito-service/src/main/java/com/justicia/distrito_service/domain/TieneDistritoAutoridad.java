package com.justicia.distrito_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tiene_distrito_autoridad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TieneDistritoAutoridad {

    @EmbeddedId
    private TieneDistritoAutoridadId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("distritoJudicialId")
    @JoinColumn(name = "distrito_judicial_id", columnDefinition = "UUID")
    private DistritoJudicial distritoJudicial;

    @Column(name = "autoridad_id", columnDefinition = "UUID", insertable = false, updatable = false)
    private UUID autoridadId;
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class TieneDistritoAutoridadId implements Serializable {
    @Column(name = "distrito_judicial_id", columnDefinition = "UUID")
    private UUID distritoJudicialId;

    @Column(name = "autoridad_id", columnDefinition = "UUID")
    private UUID autoridadId;
}
