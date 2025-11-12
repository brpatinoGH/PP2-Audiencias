package com.justicia.sala_service.controller;

import com.justicia.sala_service.dto.SalaRequest;
import com.justicia.sala_service.dto.SalaResponse;
import com.justicia.sala_service.exception.BusinessException;
import com.justicia.sala_service.exception.NotFoundException;
import com.justicia.sala_service.security.RoleValidator;
import com.justicia.sala_service.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;
    private final RoleValidator roleValidator;

    @PostMapping
    public ResponseEntity<SalaResponse> crear(@RequestBody SalaRequest request) {
        roleValidator.requireDirector();
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody SalaRequest request) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(salaService.actualizar(id, request));
    }

    @GetMapping
    public ResponseEntity<List<SalaResponse>> listarTodas() {
        return ResponseEntity.ok(salaService.listarTodas());
    }

    @GetMapping("/distrito/{distritoId}")
    public ResponseEntity<List<SalaResponse>> listarPorDistrito(@PathVariable UUID distritoId) {
        return ResponseEntity.ok(salaService.listarPorDistrito(distritoId));
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
