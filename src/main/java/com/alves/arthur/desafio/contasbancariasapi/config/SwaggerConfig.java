package com.alves.arthur.desafio.contasbancariasapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.alves.arthur.desafio.contasbancariasapi")).paths(regex("/api.*")).build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {

		ApiInfo apiInfo = new ApiInfo("Account Menagement API",
				"API para gerenciamento de contas e transações para o desafio do LTI.", "1.0", "Terms of Service",
				new Contact("Arthur Alves", "https://github.com/arthuralveso", "arthuralves91@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());

		return apiInfo;
	}
}
