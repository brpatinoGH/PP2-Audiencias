package com.justicia.sala_service.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class SalaRequest {
    private String nombre;
    private String lugar;
    private LocalDate fecha;
    private UUID distritoJudicialId;
}
