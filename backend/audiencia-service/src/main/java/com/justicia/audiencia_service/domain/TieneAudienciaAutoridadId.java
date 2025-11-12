package com.justicia.audiencia_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TieneAudienciaAutoridadId implements Serializable {
    @Column(name = "audiencia_id", columnDefinition = "UUID")
    private UUID audienciaId;

    @Column(name = "autoridad_id", columnDefinition = "UUID")
    private UUID autoridadId;
}
