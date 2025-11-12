package com.justicia.usuario_service.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tiene_usuario_distrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TieneUsuarioDistrito {

    @EmbeddedId
    private TieneUsuarioDistritoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", columnDefinition = "UUID")
    private Usuario usuario;

    @Column(name = "distrito_judicial_id", columnDefinition = "UUID", insertable = false, updatable = false)
    private UUID distritoJudicialId;
}

