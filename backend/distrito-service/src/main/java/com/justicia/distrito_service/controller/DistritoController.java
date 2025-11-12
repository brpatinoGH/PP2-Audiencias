package com.justicia.distrito_service.controller;

import com.justicia.distrito_service.dto.DistritoRequest;
import com.justicia.distrito_service.dto.DistritoResponse;
import com.justicia.distrito_service.exception.BusinessException;
import com.justicia.distrito_service.exception.NotFoundException;
import com.justicia.distrito_service.security.RoleValidator;
import com.justicia.distrito_service.service.DistritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/distritos")
@RequiredArgsConstructor
public class DistritoController {

    private final DistritoService distritoService;
    private final RoleValidator roleValidator;

    @PostMapping
    public ResponseEntity<DistritoResponse> crear(@RequestBody DistritoRequest request) {
        roleValidator.requireDirector();
        DistritoResponse creado = distritoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistritoResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody DistritoRequest request) {
        roleValidator.requireDirector();
        DistritoResponse actualizado = distritoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public ResponseEntity<List<DistritoResponse>> listarTodos() {
        return ResponseEntity.ok(distritoService.listarTodos());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DistritoResponse>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(distritoService.listarPorEstado(estado));
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
