package com.justicia.usuario_service.domain;

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
public class TieneUsuarioDistritoId implements Serializable {
    @Column(name = "usuario_id", columnDefinition = "UUID")
    private UUID usuarioId;

    @Column(name = "distrito_judicial_id", columnDefinition = "UUID")
    private UUID distritoJudicialId;
}
