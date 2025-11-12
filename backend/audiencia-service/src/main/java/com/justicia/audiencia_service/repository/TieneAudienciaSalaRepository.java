package com.justicia.audiencia_service.repository;

import com.justicia.audiencia_service.domain.TieneAudienciaSala;
import com.justicia.audiencia_service.domain.TieneAudienciaSalaId;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TieneAudienciaSalaRepository extends JpaRepository<TieneAudienciaSala, TieneAudienciaSalaId> {

    @Query("SELECT tas FROM TieneAudienciaSala tas " +
            "WHERE tas.salaId = :salaId " +
            "AND tas.audiencia.fechaInscripcion BETWEEN :inicio AND :fin")
    List<TieneAudienciaSala> findAudienciasEnSalaPorFecha(
            @Param("salaId") UUID salaId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
    List<TieneAudienciaSala> findBySalaId(UUID salaId);
}
