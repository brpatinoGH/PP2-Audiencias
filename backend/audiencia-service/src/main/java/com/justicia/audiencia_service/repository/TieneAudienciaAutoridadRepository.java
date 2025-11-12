package com.justicia.audiencia_service.repository;

import com.justicia.audiencia_service.domain.TieneAudienciaAutoridad;
import com.justicia.audiencia_service.domain.TieneAudienciaAutoridadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TieneAudienciaAutoridadRepository extends JpaRepository<TieneAudienciaAutoridad, TieneAudienciaAutoridadId> {

    List<TieneAudienciaAutoridad> findByIdAudienciaId(UUID audienciaId);

    List<TieneAudienciaAutoridad> findByIdAutoridadId(UUID autoridadId);
}
