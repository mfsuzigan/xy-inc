package com.inc.xy.poi.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value(value = "${app.title}")
	private String title;

	@Value(value = "${app.version}")
	private String version;

	@Value(value = "${app.description}")
	private String description;

	@Bean
	public Docket api() {

		ApiInfo apiInfo = new ApiInfo(title, description, version, null, null, null, null, new ArrayList<>());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.inc.xy.poi.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo);
	}
}