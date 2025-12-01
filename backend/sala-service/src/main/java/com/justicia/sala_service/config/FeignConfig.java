package com.justicia.sala_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor authFeignInterceptor() {
        return template -> {

            RequestAttributes attribs = RequestContextHolder.getRequestAttributes();

            if (attribs == null) {
                return;
            }

            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();

            if (request == null) {
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader != null) {
                template.header("Authorization", authHeader);
            }
        };
    }
}
