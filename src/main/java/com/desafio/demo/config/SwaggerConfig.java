package com.desafio.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openApi() {
	    return new OpenAPI()
	            .info(new Info()
	                    .title("API")
	                    .version("1.0")
	                    .description("API para gerenciamento de usuários"))
	            .components(new Components()
	                    .addSecuritySchemes("authJwt",
	                            new SecurityScheme()
	                                    .type(SecurityScheme.Type.HTTP)
	                                    .scheme("bearer")
	                                    .bearerFormat("JWT")));
	}

}
