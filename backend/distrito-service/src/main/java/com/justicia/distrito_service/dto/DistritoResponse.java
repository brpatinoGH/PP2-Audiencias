package com.justicia.distrito_service.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class DistritoResponse {
    private UUID id;
    private String nombre;
    private String estado;
}
