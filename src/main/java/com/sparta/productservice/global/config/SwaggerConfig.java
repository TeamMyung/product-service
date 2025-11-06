package com.sparta.productservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI productServiceOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Product Service API")
				.description("상품 관리 API 문서입니다.")
				.version("v1.0.0"));
	}
}