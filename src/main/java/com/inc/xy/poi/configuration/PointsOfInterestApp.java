package com.inc.xy.poi.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.inc.xy.poi.repository")
@ComponentScan({ "com.inc.xy.poi.controller", "com.inc.xy.poi.service", "com.inc.xy.poi.exception",
		"com.inc.xy.poi.configuration" })
@EntityScan("com.inc.xy.poi.model")
public class PointsOfInterestApp {

	public static void main(String[] args) {
		SpringApplication.run(PointsOfInterestApp.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}