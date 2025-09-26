package com.justicia.sala_service.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
@Table(name = "sala")
public class Sala extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "lugar", length = 255)
    private String lugar;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "distrito_judicial_id", columnDefinition = "UUID")
    private UUID distritoJudicialId;

}
