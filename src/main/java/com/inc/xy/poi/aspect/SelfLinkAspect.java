package com.inc.xy.poi.aspect;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.inc.xy.poi.annotation.SelfLink;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Cria, de maneira nao intrusiva, auto-links para os recursos expostos por
 * {@link Controller}s tirando proveito das informacoes da {@link Annotation}
 * {@link SelfLink} e do Spring HATEOAS
 * ({@link http://projects.spring.io/spring-hateoas/}) para obtencao do nivel de
 * maturidade de Richardson mais elevado para API (ver
 * {@link https://martinfowler.com/articles/richardsonMaturityModel.html})
 * 
 * @author Michel F. Suzigan
 *
 */
@Aspect
@Component
public class SelfLinkAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelfLinkAspect.class);

	@Autowired
	private MessageUtils messageUtils;

	/**
	 * Intercepta a saida dos metodos marcados com a {@link Annotation}
	 * {@link SelfLink} para adicao dos auto-links nos recursos
	 * 
	 */
	@AfterReturning(value = "@annotation(com.inc.xy.poi.annotation.SelfLink)", returning = "rawMethodReturnValue")
	public void addSelfLinks(JoinPoint joinPoint, Object rawMethodReturnValue) {

		if (joinPoint.getSignature() instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			SelfLink selfLinkAnnotation = methodSignature.getMethod().getAnnotation(SelfLink.class);
			Collection<ResourceSupport> methodReturnValues = prepareMethodReturnCollection(selfLinkAnnotation,
					rawMethodReturnValue);

			try {
				selfLinkEngine(selfLinkAnnotation, joinPoint.getTarget().getClass(), methodReturnValues);

			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOGGER.error(messageUtils.get("msg.self.link.error"), e);
			}
		}
	}

	/**
	 * Converte, se possivel, o retorno "cru" de um metodo de um
	 * {@link Controller} para uma lista de objetos {@link ResourceSupport}.
	 * Esta operacao e necessaria para lidar transparentemente tanto com metodos
	 * que retornam objetos unicos quanto colecoes
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Collection<ResourceSupport> prepareMethodReturnCollection(SelfLink selfLinkAnnotation,
			Object rawMethodReturnValue) {
		Collection<ResourceSupport> methodReturnValues = new ArrayList<>();

		if (selfLinkAnnotation != null && rawMethodReturnValue != null) {
			boolean matchesACollectionOfResourceSupport = rawMethodReturnValue instanceof Collection
					&& !((Collection<?>) rawMethodReturnValue).isEmpty() && ((Collection<?>) rawMethodReturnValue)
							.stream().findAny().orElse(null) instanceof ResourceSupport;

			if (matchesACollectionOfResourceSupport) {
				methodReturnValues.addAll((Collection<ResourceSupport>) rawMethodReturnValue);

			} else if (rawMethodReturnValue instanceof ResourceSupport) {
				methodReturnValues.add((ResourceSupport) rawMethodReturnValue);
			}
		}

		return methodReturnValues;
	}

	/**
	 * Identifica via reflexao os metodos necessarios tanto no
	 * {@link ResourceSupport} ({@link Entity}) quanto no {@link Controller}
	 * utilizando as informacoes da {@link Annotation} {@link SelfLink}.
	 * Adiciona em seguida o auto-link usando o {@link ControllerLinkBuilder} do
	 * Spring HATEOAS
	 * 
	 */
	private void selfLinkEngine(SelfLink selfLinkAnnotation, Class<?> controllerClass,
			Collection<ResourceSupport> returnValues)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		Method entityIdMethod = getEntityIdMethod(selfLinkAnnotation, returnValues);

		for (ResourceSupport returnValue : returnValues) {
			Object entityId = entityIdMethod == null ? null : entityIdMethod.invoke(returnValue, (Object[]) null);

			if (entityId != null) {
				Method controllerIdMethod = controllerClass
						.getDeclaredMethod(selfLinkAnnotation.controllerIdMethodName(), entityId.getClass());

				if (controllerIdMethod != null) {
					returnValue.add(linkTo(controllerIdMethod, entityId).withSelfRel());
				}
			}
		}
	}

	/**
	 * Dada uma colecao de {@link ResourceSupport}, obtem o {@link Method} na
	 * classe correspondente que retorna o id do objeto, usando as informacoes
	 * da {@link Annotation} {@link SelfLink}
	 * 
	 */
	private Method getEntityIdMethod(SelfLink selfLinkAnnotation, Collection<ResourceSupport> returnValues)
			throws NoSuchMethodException {
		Method entityIdMethod = null;

		if (CollectionUtils.isNotEmpty(returnValues)) {
			entityIdMethod = returnValues.stream().findAny().orElse(new ResourceSupport()).getClass()
					.getDeclaredMethod(selfLinkAnnotation.entityIdMethodName());
		}

		return entityIdMethod;
	}
}
