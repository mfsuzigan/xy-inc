package com.inc.xy.poi.util;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * {@link Component} utilitario para recuperacao de mensagens em bundles de
 * propriedades
 * 
 * @author Michel F. Suzigan
 *
 */
@Component
public class MessageUtils {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
	}

	/**
	 * Retorna uma propriedade/mensagem do bundle
	 * 
	 * @param key
	 *            Chave da propriedade no bundle
	 */
	public String get(String key) {
		return accessor.getMessage(key);
	}

	/**
	 * Retorna uma propriedade/mensagem do bundle preenchendo placeholders com
	 * os valores do vararg
	 * 
	 * @param key
	 *            Chave da propriedade no bundle
	 * 
	 * @param params
	 *            Valores para preenchimento de placeholders
	 */
	public String get(String key, Object... params) {

		if (params == null) {
			return get(key);
		} else {
			return accessor.getMessage(key, params);
		}
	}

}
