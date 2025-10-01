package com.justicia.audiencia_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = "com.justicia.audiencia_service.domain")

public class AudienciaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudienciaServiceApplication.class, args);
	}

}
