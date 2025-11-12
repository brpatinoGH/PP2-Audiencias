package com.justicia.usuario_service.service;

import com.justicia.usuario_service.domain.Usuario;
import com.justicia.usuario_service.domain.TieneUsuarioDistrito;
import com.justicia.usuario_service.domain.TieneUsuarioDistritoId;
import com.justicia.usuario_service.dto.LoginRequest;
import com.justicia.usuario_service.dto.LoginResponse;
import com.justicia.usuario_service.dto.UsuarioRequest;
import com.justicia.usuario_service.dto.UsuarioResponse;
import com.justicia.usuario_service.exception.BusinessException;
import com.justicia.usuario_service.exception.NotFoundException;
import com.justicia.usuario_service.repository.TieneUsuarioDistritoRepository;
import com.justicia.usuario_service.repository.UsuarioRepository;
import com.justicia.usuario_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TieneUsuarioDistritoRepository tieneUsuarioDistritoRepository;
    private final JwtUtil jwtUtil;

    // ------------------------------------
    // CU11 - Registrar Usuario
    // ------------------------------------
    @Transactional
    public UsuarioResponse crear(UsuarioRequest req) {
        validarRequest(req);

        if (usuarioRepository.existsByMailIgnoreCase(req.getMail().trim()))
            throw new BusinessException("Ya existe un usuario con este correo");

        if (req.getNombreUsuario() != null &&
                usuarioRepository.existsByNombreUsuarioIgnoreCase(req.getNombreUsuario().trim()))
            throw new BusinessException("Ya existe un usuario con este nombre de usuario");

        Usuario u = new Usuario();
        u.setNombre(req.getNombre().trim());
        u.setNombreUsuario(req.getNombreUsuario() != null ? req.getNombreUsuario().trim() : null);
        u.setMail(req.getMail().trim().toLowerCase());
        u.setContrasena(req.getContrasena().trim());
        u.setEstado("ACTIVO");
        u.setFechaInscripcion(LocalDateTime.now());
        u.setRol(Usuario.Rol.valueOf(req.getRol().trim().toUpperCase()));

        Usuario saved = usuarioRepository.save(u);

        if (req.getDistritoId() != null) {
            TieneUsuarioDistritoId id = new TieneUsuarioDistritoId(saved.getId(), req.getDistritoId());
            tieneUsuarioDistritoRepository.save(new TieneUsuarioDistrito(id, saved, req.getDistritoId()));
        }

        return mapToResponse(saved);
    }

    // ------------------------------------
    // CU12 - Actualizar Usuario
    // ------------------------------------
    @Transactional
    public UsuarioResponse actualizar(UUID id, UsuarioRequest req) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (req.getMail() != null) {
            String nuevoMail = req.getMail().trim().toLowerCase();
            if (usuarioRepository.existsByMailIgnoreCase(nuevoMail) && !u.getMail().equalsIgnoreCase(nuevoMail))
                throw new BusinessException("Ya existe un usuario con este correo");
            u.setMail(nuevoMail);
        }

        if (req.getNombreUsuario() != null) {
            String nuevoUsername = req.getNombreUsuario().trim();
            if (usuarioRepository.existsByNombreUsuarioIgnoreCase(nuevoUsername) &&
                    !nuevoUsername.equalsIgnoreCase(u.getNombreUsuario()))
                throw new BusinessException("Ya existe un usuario con este nombre de usuario");
            u.setNombreUsuario(nuevoUsername);
        }

        if (req.getNombre() != null) u.setNombre(req.getNombre().trim());
        if (req.getContrasena() != null) u.setContrasena(req.getContrasena().trim());

        if (req.getRol() != null) {
            u.setRol(Usuario.Rol.valueOf(req.getRol().trim().toUpperCase()));
        }

        if (req.getDistritoId() != null) {
            tieneUsuarioDistritoRepository.deleteByIdUsuarioId(id); // Reemplazar distrito anterior
            TieneUsuarioDistritoId idRel = new TieneUsuarioDistritoId(id, req.getDistritoId());
            tieneUsuarioDistritoRepository.save(new TieneUsuarioDistrito(idRel, u, req.getDistritoId()));
        }

        return mapToResponse(usuarioRepository.save(u));
    }

    // ------------------------------------
    // CU13 - Cambiar Estado
    // ------------------------------------
    @Transactional
    public UsuarioResponse cambiarEstado(UUID id, String nuevoEstado) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (nuevoEstado == null || nuevoEstado.isBlank())
            throw new BusinessException("Debe indicar un estado");

        String estado = nuevoEstado.trim().toUpperCase();
        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO"))
            throw new BusinessException("Estado inválido (permitidos: ACTIVO / INACTIVO)");

        u.setEstado(estado);
        return mapToResponse(usuarioRepository.save(u));
    }

    // ------------------------------------
    // CU14 - Listar Usuarios
    // ------------------------------------
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ------------------------------------
    // LOGIN
    // ------------------------------------
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        Usuario u = usuarioRepository.findByMail(req.getMail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (!u.getContrasena().equals(req.getContrasena())) {
            throw new BusinessException("Contraseña incorrecta");
        }

        String token = jwtUtil.generarToken(u.getId().toString(), u.getRol().name());

        LoginResponse res = new LoginResponse();
        res.setUsuarioId(u.getId());
        res.setNombre(u.getNombre());
        res.setRol(u.getRol().name());
        res.setToken(token);
        return res;
    }


    // ------------------------------------
    // Helpers
    // ------------------------------------
    private void validarRequest(UsuarioRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre es obligatorio");
        if (req.getMail() == null || req.getMail().isBlank())
            throw new BusinessException("El mail es obligatorio");
        if (req.getContrasena() == null || req.getContrasena().isBlank())
            throw new BusinessException("La contraseña es obligatoria");
        if (req.getRol() == null)
            throw new BusinessException("El rol es obligatorio");
    }

    private UsuarioResponse mapToResponse(Usuario u) {
        UsuarioResponse res = new UsuarioResponse();
        res.setId(u.getId());
        res.setNombre(u.getNombre());
        res.setNombreUsuario(u.getNombreUsuario());
        res.setMail(u.getMail());
        res.setEstado(u.getEstado());
        res.setRol(u.getRol().name());
        return res;
    }
}
