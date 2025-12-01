package com.justicia.autoridad_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "usuario-service", url = "http://localhost:8085")
public interface UsuarioClient {
    @GetMapping("/api/usuarios/{id}/distrito")
    UUID obtenerDistrito(@PathVariable("id") UUID usuarioId);
}
