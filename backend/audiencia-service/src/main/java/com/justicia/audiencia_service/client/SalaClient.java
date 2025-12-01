package com.justicia.audiencia_service.client;

import com.justicia.audiencia_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.UUID;

@FeignClient(
        name = "sala-service",
        url = "http://localhost:8084",
        configuration = FeignConfig.class
)public interface SalaClient {

    @GetMapping("/api/salas/{id}")
    SalaDto getSalaById(@PathVariable("id") UUID id);

    class SalaDto {
        public UUID id;
        public String nombre;
        public String lugar;
        public LocalDate fecha;
        public UUID distritoJudicialId;
    }
}
