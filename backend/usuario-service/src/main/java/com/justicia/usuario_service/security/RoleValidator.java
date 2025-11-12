package com.justicia.usuario_service.security;

import com.justicia.usuario_service.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {

    private final HttpServletRequest request;

    public void requireDirector() {
        String rol = request.getHeader("X-Rol");
        if (rol == null || !"DIRECTOR".equalsIgnoreCase(rol)) {
            throw new BusinessException("Acceso denegado: se requiere rol DIRECTOR");
        }
    }

    public void requireDirectorOrOperador() {
        String rol = request.getHeader("X-Rol");
        if (rol == null ||
                !(rol.equalsIgnoreCase("DIRECTOR") || rol.equalsIgnoreCase("OPERADOR"))) {
            throw new BusinessException("Acceso denegado: se requiere rol DIRECTOR u OPERADOR");
        }
    }

    public String getRol() {
        return request.getHeader("X-Rol");
    }
}
