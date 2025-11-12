package com.justicia.usuario_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseEntity {

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "nombre_usuario", length = 255)
    private String nombreUsuario;

    @Column(name = "mail", length = 255)
    private String mail;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", length = 20)
    private Rol rol;

    public enum Rol { DIRECTOR, OPERADOR }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() { return contrasena; }
    @Override
    public String getUsername() { return mail; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return estado.equalsIgnoreCase("ACTIVO"); }
}
