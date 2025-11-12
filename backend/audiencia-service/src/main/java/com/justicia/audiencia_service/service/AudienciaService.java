package com.justicia.audiencia_service.service;

import com.justicia.audiencia_service.client.SalaClient;
import com.justicia.audiencia_service.domain.*;
import com.justicia.audiencia_service.dto.AudienciaRequest;
import com.justicia.audiencia_service.dto.AudienciaResponse;
import com.justicia.audiencia_service.dto.AsignarAutoridadRequest;
import com.justicia.audiencia_service.exception.BusinessException;
import com.justicia.audiencia_service.exception.NotFoundException;
import com.justicia.audiencia_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudienciaService {

    private final AudienciaRepository audienciaRepository;
    private final TieneAudienciaSalaRepository tieneAudienciaSalaRepository;
    private final TieneAudienciaAutoridadRepository tieneAudienciaAutoridadRepository;
    private final SalaClient salaClient;

    @Transactional
    public AudienciaResponse crear(AudienciaRequest req) {
        validateBasicRequest(req);

        if (req.getAutoridadId() != null) {
            validarDisponibilidadAutoridad(req.getAutoridadId(), req.getFechaInscripcion(), req.getHoraInicio(), req.getDuracion());
        }

        Audiencia a = new Audiencia();
        a.setNombre(req.getNombre());
        a.setTipo(req.getTipo());
        a.setCuit(req.getCuit());
        a.setHoraInicio(req.getHoraInicio());
        a.setDuracion(req.getDuracion());
        a.setFechaInscripcion(req.getFechaInscripcion());
        a.setEstado(req.getEstado() != null ? req.getEstado() : "PROGRAMADA");
        a.setFechaCreacion(LocalDateTime.now());

        Audiencia saved = audienciaRepository.save(a);

        if (req.getAutoridadId() != null) {
            TieneAudienciaAutoridadId id = new TieneAudienciaAutoridadId(saved.getId(), req.getAutoridadId());
            TieneAudienciaAutoridad relacion = new TieneAudienciaAutoridad(id, saved, req.getAutoridadId());
            tieneAudienciaAutoridadRepository.save(relacion);
        }

        return mapToResponse(saved);
    }

    @Transactional
    public AudienciaResponse actualizar(UUID id, AudienciaRequest req) {
        Audiencia a = audienciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audiencia no encontrada"));

        if (req.getHoraInicio() != null) a.setHoraInicio(req.getHoraInicio());
        if (req.getDuracion() != null) a.setDuracion(req.getDuracion());
        if (req.getNombre() != null) a.setNombre(req.getNombre());
        if (req.getTipo() != null) a.setTipo(req.getTipo());
        if (req.getCuit() != null) a.setCuit(req.getCuit());

        if (req.getFechaInscripcion() != null || req.getHoraInicio() != null || req.getDuracion() != null) {
            List<TieneAudienciaAutoridad> autoridadesAsignadas = tieneAudienciaAutoridadRepository.findByIdAudienciaId(id);
            for (TieneAudienciaAutoridad ta : autoridadesAsignadas) {
                validarDisponibilidadAutoridad(
                        ta.getAutoridadId(),
                        req.getFechaInscripcion() != null ? req.getFechaInscripcion() : a.getFechaInscripcion(),
                        req.getHoraInicio() != null ? req.getHoraInicio() : a.getHoraInicio(),
                        req.getDuracion() != null ? req.getDuracion() : a.getDuracion()
                );
            }
        }

        a.setFechaModificacion(LocalDateTime.now());
        return mapToResponse(audienciaRepository.save(a));
    }

    @Transactional
    public AudienciaResponse cancelar(UUID id) {
        Audiencia a = audienciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audiencia no encontrada"));
        a.setEstado("CANCELADA");
        a.setFechaModificacion(LocalDateTime.now());
        return mapToResponse(audienciaRepository.save(a));
    }

    @Transactional(readOnly = true)
    public List<AudienciaResponse> listarTodas() {
        return audienciaRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AudienciaResponse> listarPorEstado(String estado) {
        return audienciaRepository.findByEstado(estado).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void asignarSala(UUID audienciaId, UUID salaId) {
        Audiencia a = audienciaRepository.findById(audienciaId)
                .orElseThrow(() -> new NotFoundException("Audiencia no encontrada"));

        try {
            SalaClient.SalaDto sala = salaClient.getSalaById(salaId);
            if (sala == null || sala.id == null) {
                throw new NotFoundException("Sala no encontrada en sala-service");
            }
        } catch (Exception ex) {
            throw new BusinessException("Error consultando sala-service: " + ex.getMessage());
        }

        validarConflictosSala(a, salaId);

        TieneAudienciaSalaId id = new TieneAudienciaSalaId(audienciaId, salaId);
        TieneAudienciaSala entidad = new TieneAudienciaSala(id, a, salaId);
        tieneAudienciaSalaRepository.save(entidad);
    }

    private void validateBasicRequest(AudienciaRequest req) {
        if (req.getHoraInicio() == null) throw new BusinessException("horaInicio es obligatorio");
        if (req.getDuracion() == null || req.getDuracion() <= 0) throw new BusinessException("duracion inválida");
        if (req.getFechaInscripcion() == null) throw new BusinessException("fechaInscripcion es obligatoria");
    }

    private void validarDisponibilidadAutoridad(UUID autoridadId, LocalDateTime fecha, LocalTime hora, Integer duracion) {
        List<TieneAudienciaAutoridad> asignaciones = tieneAudienciaAutoridadRepository.findByIdAutoridadId(autoridadId);
        for (TieneAudienciaAutoridad rel : asignaciones) {
            Audiencia other = rel.getAudiencia();
            if (other.getFechaInscripcion() != null &&
                    other.getFechaInscripcion().toLocalDate().isEqual(fecha.toLocalDate())) {
                if (isOverlap(hora, duracion, other.getHoraInicio(), other.getDuracion())) {
                    throw new BusinessException("El juez ya tiene otra audiencia en ese horario");
                }
            }
        }
    }

    private void validarConflictosSala(Audiencia a, UUID salaId) {
        List<TieneAudienciaSala> asignaciones = tieneAudienciaSalaRepository.findBySalaId(salaId);
        LocalDate date = a.getFechaInscripcion().toLocalDate();

        List<Audiencia> audienciasEnMismaFecha = asignaciones.stream()
                .map(TieneAudienciaSala::getAudiencia)
                .filter(x -> x.getFechaInscripcion() != null && x.getFechaInscripcion().toLocalDate().isEqual(date))
                .collect(Collectors.toList());

        for (Audiencia other : audienciasEnMismaFecha) {
            if (isOverlap(a.getHoraInicio(), a.getDuracion(), other.getHoraInicio(), other.getDuracion())) {
                throw new BusinessException("La sala ya está ocupada en ese horario");
            }
        }
    }

    private boolean isOverlap(LocalTime s1, Integer d1, LocalTime s2, Integer d2) {
        if (s1 == null || d1 == null || s2 == null || d2 == null) return false;
        LocalTime e1 = s1.plusMinutes(d1);
        LocalTime e2 = s2.plusMinutes(d2);
        return !(e1.compareTo(s2) <= 0 || e2.compareTo(s1) <= 0);
    }

    private AudienciaResponse mapToResponse(Audiencia a) {
        AudienciaResponse r = new AudienciaResponse();
        r.setId(a.getId());
        r.setNombre(a.getNombre());
        r.setTipo(a.getTipo());
        r.setCuit(a.getCuit());
        r.setHoraInicio(a.getHoraInicio());
        r.setDuracion(a.getDuracion());
        r.setEstado(a.getEstado());
        r.setFechaCreacion(a.getFechaCreacion());
        r.setFechaModificacion(a.getFechaModificacion());
        r.setFechaInscripcion(a.getFechaInscripcion());
        return r;
    }
}
