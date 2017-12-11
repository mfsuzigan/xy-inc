package com.inc.xy.poi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configura a seguranca na aplicacao, requerendo para todas as requisicoes
 * autenticacao BASIC<br/>
 * <br/>
 * Observacao: a protecao contra CSRF foi desabilitada para efeito de
 * simplicidade na implementacao
 *
 * @author Michel F. Suzigan
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${app.user}")
	private String appUser;

	@Value("${app.password}")
	private String appPassword;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(appUser).password(appPassword).roles("USER");
	}
}