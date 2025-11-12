package com.justicia.autoridad_service.controller;

import com.justicia.autoridad_service.dto.AutoridadRequest;
import com.justicia.autoridad_service.dto.AutoridadResponse;
import com.justicia.autoridad_service.exception.BusinessException;
import com.justicia.autoridad_service.exception.NotFoundException;
import com.justicia.autoridad_service.security.RoleValidator;
import com.justicia.autoridad_service.service.AutoridadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/autoridades")
@RequiredArgsConstructor
public class AutoridadController {

    private final AutoridadService autoridadService;
    private final RoleValidator roleValidator; // ✅ agregado

    @PostMapping
    public ResponseEntity<AutoridadResponse> crear(@RequestBody AutoridadRequest request) {
        roleValidator.requireDirector();
        AutoridadResponse response = autoridadService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoridadResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody AutoridadRequest request) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(autoridadService.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<AutoridadResponse>> listarTodas() {
        return ResponseEntity.ok(autoridadService.listarTodas());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<AutoridadResponse>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(autoridadService.listarPorTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AutoridadResponse>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(autoridadService.listarPorEstado(estado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AutoridadResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam String nuevoEstado) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(autoridadService.cambiarEstado(id, nuevoEstado));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
