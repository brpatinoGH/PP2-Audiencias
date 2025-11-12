package com.justicia.autoridad_service.service;

import com.justicia.autoridad_service.domain.Autoridad;
import com.justicia.autoridad_service.dto.AutoridadRequest;
import com.justicia.autoridad_service.dto.AutoridadResponse;
import com.justicia.autoridad_service.exception.BusinessException;
import com.justicia.autoridad_service.exception.NotFoundException;
import com.justicia.autoridad_service.repository.AutoridadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoridadService {

    private final AutoridadRepository autoridadRepository;

    @Transactional
    public AutoridadResponse crear(AutoridadRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre es obligatorio");

        Autoridad autoridad = new Autoridad();
        autoridad.setNombre(req.getNombre());
        autoridad.setMail(req.getMail());
        autoridad.setEstado(req.getEstado() != null ? req.getEstado() : "ACTIVO");

        if (req.getTipo() != null && !req.getTipo().isBlank()) {
            try {
                autoridad.setTipo(Autoridad.Tipo.valueOf(req.getTipo().trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new BusinessException("Tipo de autoridad inválido. Valores válidos: JUEZ, FISCAL, DEFENSOR");
            }
        }

        Autoridad saved = autoridadRepository.save(autoridad);
        return mapToResponse(saved);
    }

    @Transactional
    public AutoridadResponse actualizar(UUID id, AutoridadRequest req) {
        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autoridad no encontrada"));

        if (req.getNombre() != null) autoridad.setNombre(req.getNombre());
        if (req.getMail() != null) autoridad.setMail(req.getMail());
        if (req.getEstado() != null) autoridad.setEstado(req.getEstado());
        if (req.getTipo() != null) {
            try {
                autoridad.setTipo(Autoridad.Tipo.valueOf(req.getTipo().trim().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new BusinessException("Tipo de autoridad inválido. Valores válidos: JUEZ, FISCAL, DEFENSOR");
            }
        }

        return mapToResponse(autoridadRepository.save(autoridad));
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
        Autoridad autoridad = autoridadRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autoridad no encontrada"));

        autoridad.setEstado(nuevoEstado);

        return mapToResponse(autoridadRepository.save(autoridad));
    }

    private void validarRequest(AutoridadRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre de la autoridad es obligatorio");
        if (req.getMail() == null || req.getMail().isBlank())
            throw new BusinessException("El mail de la autoridad es obligatorio");
        if (req.getTipo() == null)
            throw new BusinessException("El tipo de autoridad es obligatorio");
    }

    private AutoridadResponse mapToResponse(Autoridad a) {
        AutoridadResponse res = new AutoridadResponse();
        res.setId(a.getId());
        res.setNombre(a.getNombre());
        res.setMail(a.getMail());
        res.setEstado(a.getEstado());
        res.setTipo(a.getTipo() != null ? a.getTipo().name() : null);
        return res;
    }

}
