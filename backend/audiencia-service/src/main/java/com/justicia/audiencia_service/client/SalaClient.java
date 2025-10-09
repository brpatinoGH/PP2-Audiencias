package com.justicia.audiencia_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "sala-service", url = "${sala.service.url:http://localhost:8083}")
public interface SalaClient {

    @GetMapping("/api/salas/{id}")
    SalaDto getSalaById(@PathVariable("id") UUID id);

    class SalaDto {
        public UUID id;
        public String nombre;
        public String lugar;
    }
}
