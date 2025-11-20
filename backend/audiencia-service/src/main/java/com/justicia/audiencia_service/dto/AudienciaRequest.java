package com.justicia.audiencia_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AudienciaRequest {
    private String nombre;
    private String tipo;
    private String cuit;

    private LocalTime horaInicio;
    private Integer duracion;
    private LocalDateTime fechaInscripcion;
    private String estado;

    private UUID autoridadId;
    private UUID distritoId;

}
