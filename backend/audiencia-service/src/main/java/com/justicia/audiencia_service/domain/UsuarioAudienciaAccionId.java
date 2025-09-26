package com.justicia.audiencia_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UsuarioAudienciaAccionId implements Serializable {

    @Column(name = "usuario_id", columnDefinition = "UUID")
    private UUID usuarioId;

    @Column(name = "audiencia_id", columnDefinition = "UUID")
    private UUID audienciaId;

    // Getters, Setters, equals, hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioAudienciaAccionId)) return false;
        UsuarioAudienciaAccionId that = (UsuarioAudienciaAccionId) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(audienciaId, that.audienciaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, audienciaId);
    }
}
