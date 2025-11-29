package com.justicia.audiencia_service.client;

import com.justicia.audiencia_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "autoridad-service", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface AutoridadClient {

    @GetMapping("/api/autoridades/{id}")
    AutoridadDto getAutoridadById(@PathVariable("id") UUID id);

    class AutoridadDto {
        public UUID id;
        public String nombre;
        public String apellido;
        public String tipo;
        public String mail;
        public String estado;
    }
}