package com.justicia.usuario_service.repository;

import com.justicia.usuario_service.domain.TieneUsuarioDistrito;
import com.justicia.usuario_service.domain.TieneUsuarioDistritoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TieneUsuarioDistritoRepository extends JpaRepository<TieneUsuarioDistrito, TieneUsuarioDistritoId> {

    void deleteByIdUsuarioId(UUID usuarioId);
}
