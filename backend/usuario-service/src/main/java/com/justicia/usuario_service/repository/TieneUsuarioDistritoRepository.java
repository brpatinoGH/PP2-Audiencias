package com.justicia.usuario_service.repository;

import com.justicia.usuario_service.domain.TieneUsuarioDistrito;
import com.justicia.usuario_service.domain.TieneUsuarioDistritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TieneUsuarioDistritoRepository extends JpaRepository<TieneUsuarioDistrito, TieneUsuarioDistritoId> {

    void deleteByIdUsuarioId(UUID usuarioId);

    @Query("SELECT t.distritoJudicialId FROM TieneUsuarioDistrito t WHERE t.id.usuarioId = :usuarioId")
    Optional<UUID> findDistritoIdByUsuarioId(UUID usuarioId);

}
