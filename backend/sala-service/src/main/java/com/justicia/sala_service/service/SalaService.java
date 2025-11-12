package com.justicia.sala_service.service;

import com.justicia.sala_service.domain.Sala;
import com.justicia.sala_service.dto.SalaRequest;
import com.justicia.sala_service.dto.SalaResponse;
import com.justicia.sala_service.exception.BusinessException;
import com.justicia.sala_service.exception.NotFoundException;
import com.justicia.sala_service.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;

    @Transactional
    public SalaResponse crear(SalaRequest req) {
        validarRequest(req);

        // Validar que no exista una sala con el mismo nombre en el mismo distrito
        if (salaRepository.existsByNombreAndDistritoJudicialId(req.getNombre(), req.getDistritoJudicialId())) {
            throw new BusinessException("Ya existe una sala con ese nombre en el mismo distrito judicial");
        }

        Sala sala = new Sala();
        sala.setNombre(req.getNombre());
        sala.setLugar(req.getLugar());
        sala.setFecha(req.getFecha());
        sala.setDistritoJudicialId(req.getDistritoJudicialId());

        Sala saved = salaRepository.save(sala);
        return mapToResponse(saved);
    }

    @Transactional
    public SalaResponse actualizar(UUID id, SalaRequest req) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sala no encontrada"));

        if (req.getNombre() != null) sala.setNombre(req.getNombre());
        if (req.getLugar() != null) sala.setLugar(req.getLugar());
        if (req.getFecha() != null) sala.setFecha(req.getFecha());
        if (req.getDistritoJudicialId() != null) sala.setDistritoJudicialId(req.getDistritoJudicialId());

        return mapToResponse(salaRepository.save(sala));
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> listarTodas() {
        return salaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> listarPorDistrito(UUID distritoId) {
        return salaRepository.findByDistritoJudicialId(distritoId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void validarRequest(SalaRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank())
            throw new BusinessException("El nombre de la sala es obligatorio");
        if (req.getLugar() == null || req.getLugar().isBlank())
            throw new BusinessException("El lugar de la sala es obligatorio");
        if (req.getDistritoJudicialId() == null)
            throw new BusinessException("Debe asociarse la sala a un distrito judicial");
    }

    private SalaResponse mapToResponse(Sala s) {
        SalaResponse res = new SalaResponse();
        res.setId(s.getId());
        res.setNombre(s.getNombre());
        res.setLugar(s.getLugar());
        res.setFecha(s.getFecha());
        res.setDistritoJudicialId(s.getDistritoJudicialId());
        return res;
    }
}
