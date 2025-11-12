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
public class UsuarioAudienciaAccionId implements Serializable {

    @Column(name = "usuario_id", columnDefinition = "UUID")
    private UUID usuarioId;

    @Column(name = "audiencia_id", columnDefinition = "UUID")
    private UUID audienciaId;

}
