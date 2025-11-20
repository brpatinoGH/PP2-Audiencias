package com.justicia.audiencia_service.config;

import feign.Logger;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return template -> {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attrs == null) return;

            HttpServletRequest request = attrs.getRequest();
            if (request == null) return;

            String auth = request.getHeader("Authorization");
            String rol = request.getHeader("X-Rol");
            String usuarioId = request.getHeader("X-Usuario-Id");

            if (auth != null)     template.header("Authorization", auth);
            if (rol != null)      template.header("X-Rol", rol);
            if (usuarioId != null) template.header("X-Usuario-Id", usuarioId);

            System.out.println("FEIGN → Mandando headers:");
            System.out.println("Authorization = " + auth);
            System.out.println("X-Rol = " + rol);
            System.out.println("X-Usuario-Id = " + usuarioId);

        };
    }
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
