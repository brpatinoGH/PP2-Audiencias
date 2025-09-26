package com.justicia.usuario_service.domain;

import jakarta.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    protected UUID id;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
}
