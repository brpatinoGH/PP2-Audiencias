package com.justicia.autoridad_service.service;

import com.justicia.autoridad_service.domain.Autoridad;
import com.justicia.autoridad_service.dto.AutoridadRequest;
import com.justicia.autoridad_service.dto.AutoridadResponse;
import com.justicia.autoridad_service.exception.BusinessException;
import com.justicia.autoridad_service.exception.NotFoundException;
import com.justicia.autoridad_service.repository.AutoridadRepository;
import com.justicia.autoridad_service.client.UsuarioClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoridadService {

    private final AutoridadRepository autoridadRepository;
    private final UsuarioClient usuarioClient;
    private final HttpServletRequest httpReq;

    @Transactional
    public AutoridadResponse crear(AutoridadRequest req) {

        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre es obligatorio");

        if (req.getTipo() == null)
            throw new BusinessException("El tipo es obligatorio");

        if (req.getDistritoId() == null)
            throw new BusinessException("El distrito es obligatorio");

        String rol = httpReq.getHeader("X-Rol");
        UUID usuarioId = UUID.fromString(httpReq.getHeader("X-Usuario-Id"));

        System.out.println("DEBUG >> ROL LLEGÓ A AUTORIDAD SERVICE = " + rol);
        System.out.println("DEBUG >> USER_ID LLEGÓ A AUTORIDAD SERVICE = " + usuarioId);

        if (rol.equalsIgnoreCase("OPERADOR")) {

            UUID distritoOperador = usuarioClient.obtenerDistrito(usuarioId);

            if (!distritoOperador.equals(req.getDistritoId())) {
                throw new BusinessException("El operador solo puede gestionar su propio distrito");
            }
        }

        Autoridad autoridad = new Autoridad();
        autoridad.setNombre(req.getNombre());
        autoridad.setMail(req.getMail());
        autoridad.setEstado("ACTIVO");
        autoridad.setDistritoId(req.getDistritoId());

        try {
            autoridad.setTipo(Autoridad.Tipo.valueOf(req.getTipo().trim().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Tipo inválido. Valores válidos: JUEZ, FISCAL, DEFENSOR");
        }

        Autoridad saved = autoridadRepository.save(autoridad);
        return mapToResponse(saved);
    }

    @Transactional
    public AutoridadResponse actualizar(UUID id, AutoridadRequest req) {

        String rol = httpReq.getHeader("X-Rol");
        UUID usuarioId = UUID.fromString(httpReq.getHeader("X-Usuario-Id"));

        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autoridad no encontrada"));

        if (rol.equalsIgnoreCase("OPERADOR")) {
            UUID distritoOperador = usuarioClient.obtenerDistrito(usuarioId);

            if (!distritoOperador.equals(autoridad.getDistritoId())) {
                throw new BusinessException("El operador no puede modificar autoridades de otro distrito");
            }
        }

        if (req.getNombre() != null) autoridad.setNombre(req.getNombre());
        if (req.getMail() != null) autoridad.setMail(req.getMail());
        if (req.getEstado() != null) autoridad.setEstado(req.getEstado());
        if (req.getTipo() != null) {
            try {
                autoridad.setTipo(Autoridad.Tipo.valueOf(req.getTipo().trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new BusinessException("Tipo inválido. Valores válidos: JUEZ, FISCAL, DEFENSOR");
            }
        }

        return mapToResponse(autoridadRepository.save(autoridad));
    }

    @Transactional(readOnly = true)
    public AutoridadResponse buscarPorId(UUID id) {
        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autoridad no encontrada con id: " + id));

        return mapToResponse(autoridad);
    }

    @Transactional(readOnly = true)
    public List<AutoridadResponse> listarTodas() {
        return autoridadRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutoridadResponse> listarPorTipo(String tipo) {
        return autoridadRepository.findByTipo(Autoridad.Tipo.valueOf(tipo.toUpperCase()))
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutoridadResponse> listarPorEstado(String estado) {
        return autoridadRepository.findByEstadoIgnoreCase(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AutoridadResponse cambiarEstado(UUID id, String nuevoEstado) {

        String rol = httpReq.getHeader("X-Rol");
        UUID usuarioId = UUID.fromString(httpReq.getHeader("X-Usuario-Id"));

        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autoridad no encontrada"));

        if (rol.equalsIgnoreCase("OPERADOR")) {
            UUID distritoOperador = usuarioClient.obtenerDistrito(usuarioId);

            if (!distritoOperador.equals(autoridad.getDistritoId())) {
                throw new BusinessException("No puede cambiar el estado de autoridades de otro distrito");
            }
        }

        autoridad.setEstado(nuevoEstado);
        return mapToResponse(autoridadRepository.save(autoridad));
    }

    @Transactional
    public void eliminarFisicamente(UUID id){
        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sala no encontrada"));

        autoridadRepository.delete(autoridad);
        autoridadRepository.flush();
    }

    private AutoridadResponse mapToResponse(Autoridad a) {
        AutoridadResponse res = new AutoridadResponse();
        res.setId(a.getId());
        res.setNombre(a.getNombre());
        res.setApellido(a.getApellido());
        res.setMail(a.getMail());
        res.setEstado(a.getEstado());
        res.setTipo(a.getTipo() != null ? a.getTipo().name() : null);
        res.setDistritoId(a.getDistritoId());
        return res;
    }

}
