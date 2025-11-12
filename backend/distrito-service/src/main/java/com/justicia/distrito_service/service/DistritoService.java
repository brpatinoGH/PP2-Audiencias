package com.justicia.distrito_service.service;

import com.justicia.distrito_service.domain.DistritoJudicial;
import com.justicia.distrito_service.dto.DistritoRequest;
import com.justicia.distrito_service.dto.DistritoResponse;
import com.justicia.distrito_service.exception.BusinessException;
import com.justicia.distrito_service.exception.NotFoundException;
import com.justicia.distrito_service.repository.DistritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistritoService {

    private final DistritoRepository distritoRepository;

    @Transactional
    public DistritoResponse crear(DistritoRequest req) {
        validarRequest(req);

        if (distritoRepository.existsByNombreIgnoreCase(req.getNombre())) {
            throw new BusinessException("Ya existe un distrito judicial con ese nombre");
        }

        DistritoJudicial distrito = new DistritoJudicial();
        distrito.setNombre(req.getNombre());
        distrito.setEstado(req.getEstado() != null ? req.getEstado() : "ACTIVO");

        DistritoJudicial saved = distritoRepository.save(distrito);
        return mapToResponse(saved);
    }

    @Transactional
    public DistritoResponse actualizar(UUID id, DistritoRequest req) {
        DistritoJudicial distrito = distritoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Distrito judicial no encontrado"));

        if (req.getNombre() != null && !req.getNombre().isBlank())
            distrito.setNombre(req.getNombre());

        if (req.getEstado() != null && !req.getEstado().isBlank())
            distrito.setEstado(req.getEstado());

        return mapToResponse(distritoRepository.save(distrito));
    }


    @Transactional(readOnly = true)
    public List<DistritoResponse> listarTodos() {
        return distritoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DistritoResponse> listarPorEstado(String estado) {
        return distritoRepository.findByEstadoIgnoreCase(estado)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private void validarRequest(DistritoRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre del distrito judicial es obligatorio");
    }

    private DistritoResponse mapToResponse(DistritoJudicial d) {
        DistritoResponse res = new DistritoResponse();
        res.setId(d.getId());
        res.setNombre(d.getNombre());
        res.setEstado(d.getEstado());
        return res;
    }
}
