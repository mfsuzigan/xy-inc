package com.inc.xy.poi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.inc.xy.poi.repository")
@ComponentScan({ "com.inc.xy.poi.controller", "com.inc.xy.poi.service" })
@EntityScan("com.inc.xy.poi.model")
public class PointsOfInterestApp {

	public static void main(String[] args) {
		SpringApplication.run(PointsOfInterestApp.class, args);
	}
}