package com.justicia.autoridad_service.dto;

import com.justicia.autoridad_service.domain.Autoridad;
import lombok.Data;
import java.util.UUID;

@Data
public class AutoridadResponse {
    private UUID id;
    private String nombre;
    private String mail;
    private String estado;
    private String tipo;
}
