package com.justicia.usuario_service.repository;

import com.justicia.usuario_service.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByMail(String mail);

    boolean existsByMailIgnoreCase(String mail);

    boolean existsByNombreUsuarioIgnoreCase(String nombreUsuario);
}
