package com.inc.xy.poi.util;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
	}

	public String get(String key) {
		return accessor.getMessage(key);
	}

	public String get(String key, Object... params) {

		if (params == null) {
			return get(key);
		} else {
			return accessor.getMessage(key, params);
		}
	}

}
