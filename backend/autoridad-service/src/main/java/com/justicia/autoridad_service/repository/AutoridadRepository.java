package com.justicia.autoridad_service.repository;

import com.justicia.autoridad_service.domain.Autoridad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AutoridadRepository extends JpaRepository<Autoridad, UUID> {
    List<Autoridad> findByTipo(Autoridad.Tipo tipo);
    List<Autoridad> findByEstado(String estado);
    List<Autoridad> findByEstadoIgnoreCase(String estado);

}
