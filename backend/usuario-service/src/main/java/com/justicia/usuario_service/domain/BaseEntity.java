package com.justicia.usuario_service.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    protected UUID id;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    public abstract String getPassword();

    public abstract String getUsername();

    public abstract boolean isAccountNonExpired();

    public abstract boolean isAccountNonLocked();

    public abstract boolean isCredentialsNonExpired();

    public abstract boolean isEnabled();
}
