package com.justicia.audiencia_service.controller;

import com.justicia.audiencia_service.dto.AudienciaRequest;
import com.justicia.audiencia_service.dto.AudienciaResponse;
import com.justicia.audiencia_service.security.RoleValidator;
import com.justicia.audiencia_service.service.AudienciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audiencias")
@RequiredArgsConstructor
public class AudienciaController {

    private final AudienciaService audienciaService;
    private final RoleValidator roleValidator;
    private final HttpServletRequest httpReq;

    @PostMapping
    public ResponseEntity<AudienciaResponse> crear(@RequestBody AudienciaRequest request) {
        roleValidator.requireDirectorOrOperador();
        System.out.println("HEADER X-Rol = " + httpReq.getHeader("X-Rol"));
        System.out.println("HEADER X-Usuario-Id = " + httpReq.getHeader("X-Usuario-Id"));
        return ResponseEntity.ok(audienciaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AudienciaResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody AudienciaRequest request) {
        roleValidator.requireDirectorOrOperador();
        return ResponseEntity.ok(audienciaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AudienciaResponse> cancelar(@PathVariable UUID id) {
        roleValidator.requireDirectorOrOperador();
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
        roleValidator.requireDirectorOrOperador();
        audienciaService.asignarSala(audienciaId, salaId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String test(HttpServletRequest rq) {
        System.out.println("LLEGÓ AL TEST");
        System.out.println("X-Rol = " + rq.getHeader("X-Rol"));
        System.out.println("X-Usuario-Id = " + rq.getHeader("X-Usuario-Id"));
        return "OK";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        roleValidator.requireDirectorOrOperador();

        audienciaService.eliminarFisicamente(id);
        return ResponseEntity.noContent().build();
    }

}
