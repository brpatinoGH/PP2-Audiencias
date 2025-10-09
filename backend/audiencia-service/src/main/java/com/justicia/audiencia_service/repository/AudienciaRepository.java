package com.justicia.audiencia_service.repository;

import com.justicia.audiencia_service.domain.Audiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AudienciaRepository extends JpaRepository<Audiencia, UUID> {

    List<Audiencia> findByEstado(String estado);

    List<Audiencia> findByTipo(String tipo);

    List<Audiencia> findByCuit(String cuit);

    List<Audiencia> findByFechaInscripcionBetween(LocalDateTime from, LocalDateTime to);
}
