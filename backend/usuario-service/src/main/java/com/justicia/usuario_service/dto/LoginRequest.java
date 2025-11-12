package com.justicia.usuario_service.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String contrasena;
}
