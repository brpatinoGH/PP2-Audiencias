package com.justicia.sala_service.repository;

import com.justicia.sala_service.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SalaRepository extends JpaRepository<Sala, UUID> {
    List<Sala> findByDistritoJudicialId(UUID distritoId);
    boolean existsByNombreAndDistritoJudicialId(String nombre, UUID distritoJudicialId);
}
