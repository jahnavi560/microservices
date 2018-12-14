package com.webservices.restfulwebservice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("API Title", "API Description", "1.0", "urn:tos", "jahnavi", "Apache 2.0",  "http://www.apache.org/licenses/LICENSE-2.0");
	private static final Set<String> DEFAILT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json","application/xml"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.produces(DEFAILT_PRODUCES_CONSUMES)
				.consumes(DEFAILT_PRODUCES_CONSUMES);
		
	}

}
