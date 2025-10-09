package com.justicia.audiencia_service.controller;

import com.justicia.audiencia_service.dto.AudienciaRequest;
import com.justicia.audiencia_service.dto.AudienciaResponse;
import com.justicia.audiencia_service.service.AudienciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audiencias")
@RequiredArgsConstructor
public class AudienciaController {

    private final AudienciaService audienciaService;

    @PostMapping
    public ResponseEntity<AudienciaResponse> crear(@RequestBody AudienciaRequest request) {
        return ResponseEntity.ok(audienciaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudienciaResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody AudienciaRequest request) {
        return ResponseEntity.ok(audienciaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AudienciaResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(audienciaService.cancelar(id));
    }


    @GetMapping
    public ResponseEntity<List<AudienciaResponse>> listarTodas() {
        return ResponseEntity.ok(audienciaService.listarTodas());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AudienciaResponse>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(audienciaService.listarPorEstado(estado));
    }


    @PostMapping("/{audienciaId}/sala/{salaId}")
    public ResponseEntity<Void> asignarSala(
            @PathVariable UUID audienciaId,
            @PathVariable UUID salaId) {
        audienciaService.asignarSala(audienciaId, salaId);
        return ResponseEntity.ok().build();
    }

}
