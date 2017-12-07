package com.inc.xy.poi.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.inc.xy.poi.exception.ValidationException;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.repository.PointRepository;
import com.inc.xy.poi.util.MessageUtils;

public class PointServiceTest {

	@InjectMocks
	private PointService service;

	@Mock
	private Validator validator;

	@Mock
	private PointRepository pointRepository;

	@Mock
	private MessageUtils messageUtils;

	@Mock
	private Point mockPoint;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private ResourceBundle validationMessages = ResourceBundle.getBundle("ValidationMessages");

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		Arrays.asList("msg.null.point").forEach(this::prepareMessageInMock);
	}

	@Test
	public void saveTest_Failure_NullPoint() {
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage(validationMessages.getString("msg.null.point"));
		service.save(null);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void saveTest_Failure_ConstraintViolation() {
		expectedException.expect(ValidationException.class);
		Set<ConstraintViolation<Point>> constraintViolation = Mockito.mock(Set.class);
		Point invalidPoint = new Point(null, null, null);
		Mockito.when(validator.validate(invalidPoint)).thenReturn(constraintViolation);
		service.save(invalidPoint);
	}

	@Test
	public void saveTest_Success() {
		Mockito.when(pointRepository.save(mockPoint)).thenReturn(mockPoint);
		Assert.assertEquals(service.save(mockPoint), mockPoint);
	}

	@Test
	public void findInRadiusTest_Success() {
		Point pointInRange1 = new Point("inRange1", BigDecimal.ZERO, new BigDecimal(1));
		Point pointInRange2 = new Point("inRange2", BigDecimal.ZERO, new BigDecimal(2));
		Point pointOutOfRange = new Point("outOfRange", BigDecimal.ZERO, new BigDecimal(4));
		Mockito.when(service.findAll()).thenReturn(Arrays.asList(pointInRange1, pointInRange2, pointOutOfRange));

		List<Point> pointsInRadius = service.findInRadius(new Point("center", BigDecimal.ZERO, BigDecimal.ZERO),
				new BigDecimal(3));
		Assert.assertTrue(pointsInRadius.containsAll(Arrays.asList(pointInRange1, pointInRange2)));
		Assert.assertFalse(pointsInRadius.containsAll(Arrays.asList(pointOutOfRange)));
	}

	private void prepareMessageInMock(String messageKey) {
		Mockito.when(messageUtils.get(messageKey)).thenReturn(validationMessages.getString(messageKey));
	}
}
