package com.justicia.audiencia_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_audiencia_accion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAudienciaAccion {

    @EmbeddedId
    private UsuarioAudienciaAccionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("audienciaId")
    @JoinColumn(name = "audiencia_id", columnDefinition = "UUID")
    private Audiencia audiencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion", length = 20)
    private Accion accion;

    public enum Accion { CARGA, MODIFICAR, ELIMINAR }
}
