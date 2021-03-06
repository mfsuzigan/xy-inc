package com.inc.xy.poi.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configura o escaneamento e a inicializacao do Spring Boot para a aplicacao
 * 
 * @author Michel F. Suzigan
 *
 */
@SpringBootApplication
@EnableJpaRepositories("com.inc.xy.poi.repository")
@ComponentScan({ "com.inc.xy.poi.*" })
public class ApplicationConfig {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

	/**
	 * Cria o bean para recuperacao de mensagens do bundle de
	 * ValidationMessages.properties
	 * 
	 */
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasename("classpath:ValidationMessages");
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		reloadableResourceBundleMessageSource.setCacheSeconds(-1);
		return reloadableResourceBundleMessageSource;
	}
}