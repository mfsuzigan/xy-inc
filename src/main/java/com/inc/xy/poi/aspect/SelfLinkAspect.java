package com.inc.xy.poi.aspect;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import com.inc.xy.poi.annotation.SelfLink;
import com.inc.xy.poi.util.MessageUtils;

@Aspect
@Component
public class SelfLinkAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelfLinkAspect.class);

	@Autowired
	private MessageUtils messageUtils;

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
