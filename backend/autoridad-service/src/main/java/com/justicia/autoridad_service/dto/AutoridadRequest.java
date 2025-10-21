package com.justicia.autoridad_service.dto;

import com.justicia.autoridad_service.domain.Autoridad;
import lombok.Data;

@Data
public class AutoridadRequest {
    private String nombre;
    private String mail;
    private String estado; // ACTIVO o INACTIVO
    private String tipo; // Enum (JUEZ, FISCAL, DEFENSOR)
}
