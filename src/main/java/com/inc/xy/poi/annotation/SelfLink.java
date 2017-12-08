package com.inc.xy.poi.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Controller;

/**
 * Habilita a adicao transparente de auto-links na saida de metodos de
 * {@link Controller}s tirando proveito das informacoes da {@link Annotation}
 * {@link SelfLink} e do Spring HATEOAS
 * ({@link http://projects.spring.io/spring-hateoas/}) para obtencao do nivel de
 * maturidade de Richardson mais elevado para API (ver
 * {@link https://martinfowler.com/articles/richardsonMaturityModel.html})
 * 
 * @author Michel F. Suzigan
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfLink {

	/**
	 * Nome nao qualificado do metodo no {@link Controller} que retorna um
	 * recurso pelo seu identificador
	 */
	String controllerIdMethodName();

	/**
	 * Nome nao qualificado do getter no {@link ResourceSupport} que retorna seu
	 * identificador
	 */
	String entityIdMethodName();
}
