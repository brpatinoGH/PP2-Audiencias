package com.justicia.distrito_service.repository;

import com.justicia.distrito_service.domain.DistritoJudicial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DistritoRepository extends JpaRepository<DistritoJudicial, UUID> {
    boolean existsByNombreIgnoreCase(String nombre);
    List<DistritoJudicial> findByEstadoIgnoreCase(String estado);
}
