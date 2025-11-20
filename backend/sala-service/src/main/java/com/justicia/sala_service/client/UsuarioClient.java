package com.justicia.sala_service.client;

import com.justicia.sala_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "usuario-service",
        url = "${usuario.service.url:http://localhost:8080}",
        configuration = FeignConfig.class
)
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}/distrito")
    UUID obtenerDistrito(@PathVariable("id") UUID id);
}