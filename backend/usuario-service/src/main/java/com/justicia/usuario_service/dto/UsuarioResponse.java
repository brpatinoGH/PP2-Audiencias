package com.justicia.usuario_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UsuarioResponse {
    private UUID id;
    private String nombre;
    private String nombreUsuario;
    private String mail;
    private String estado;
    private LocalDateTime fechaInscripcion;
    private String rol;
}
