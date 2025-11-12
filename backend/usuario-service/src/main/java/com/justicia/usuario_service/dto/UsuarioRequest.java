package com.justicia.usuario_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UsuarioRequest {
    private String nombre;
    private String nombreUsuario;
    private String mail;
    private String contrasena;
    private String rol;
    private UUID distritoId;
}

