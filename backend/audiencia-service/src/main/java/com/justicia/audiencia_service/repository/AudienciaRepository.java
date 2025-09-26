package com.justicia.audiencia_service.repository;

import com.justicia.audiencia_service.domain.Audiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AudienciaRepository extends JpaRepository<Audiencia, UUID> {

}
