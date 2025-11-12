package com.justicia.usuario_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponse {
    private UUID usuarioId;
    private String nombre;
    private String rol;
    private String token;
}
