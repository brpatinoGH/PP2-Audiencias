package com.justicia.usuario_service.controller;

import com.justicia.usuario_service.dto.LoginRequest;
import com.justicia.usuario_service.dto.LoginResponse;
import com.justicia.usuario_service.dto.UsuarioRequest;
import com.justicia.usuario_service.dto.UsuarioResponse;
import com.justicia.usuario_service.security.RoleValidator;
import com.justicia.usuario_service.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RoleValidator roleValidator; // ✅ agregado

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioRequest request) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(usuarioService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable UUID id,
            @RequestBody UsuarioRequest request) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<UsuarioResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam String estado) {
        roleValidator.requireDirector();
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, estado));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        roleValidator.requireDirector();
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req, HttpServletRequest request) {
        LoginResponse res = usuarioService.login(req);
        request.getSession(true).setAttribute("usuarioId", res.getUsuarioId());
        request.getSession().setAttribute("rol", res.getRol());
        return ResponseEntity.ok(res);
    }
}
