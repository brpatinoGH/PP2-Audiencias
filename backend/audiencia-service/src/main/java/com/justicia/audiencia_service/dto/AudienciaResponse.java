package com.justicia.audiencia_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AudienciaResponse {
    private UUID id;
    private String nombre;
    private String tipo;
    private String cuit;

    private LocalTime horaInicio;
    private Integer duracion;
    private String estado;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaInscripcion;

    private String salaNombre;
    private String autoridadNombre;
    private String autoridadApellido;
    private String autoridadTipo;
}
