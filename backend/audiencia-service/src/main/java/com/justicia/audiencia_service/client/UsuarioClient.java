package com.justicia.audiencia_service.client;

import com.justicia.audiencia_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "usuario-service",
        url = "http://localhost:8085",
        configuration = FeignConfig.class
)
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}/distrito")
    UUID obtenerDistrito(@PathVariable("id") UUID usuarioId);
}
