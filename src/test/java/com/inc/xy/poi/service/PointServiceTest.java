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
import org.springframework.dao.EmptyResultDataAccessException;

import com.inc.xy.poi.exception.ValidationException;
import com.inc.xy.poi.model.Point;
import com.inc.xy.poi.repository.PointRepository;
import com.inc.xy.poi.util.MessageUtils;

/**
 * Testes unitarios para a camada de negocio das operacoes de {@link Point}
 * 
 * @author Michel F. Suzigan
 *
 */
public class PointServiceTest {

	@InjectMocks
	private PointService service;

	@Mock
	private Validator validator;

	@Mock
	private PointRepository repository;

	@Mock
	private MessageUtils messageUtils;

	@Mock
	private Point mockPoint;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private ResourceBundle testMessages = ResourceBundle.getBundle("ValidationMessages");

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		Arrays.asList("msg.null.point", "msg.invalid.distance", "msg.point.id.required", "msg.point.not.found")
				.forEach(this::prepareMessageInMock);
	}

	@Test
	public void saveTest_Failure_NullPoint() {
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage(testMessages.getString("msg.null.point"));
		service.save(null);
	}

	@Test
	public void saveTest_Failure_ConstraintViolation() {
		expectedException.expect(ValidationException.class);
		service.save(buildInvalidPoint());
	}

	@Test
	public void saveTest_Success() {
		Mockito.when(repository.save(mockPoint)).thenReturn(mockPoint);
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
		Assert.assertFalse(pointsInRadius.contains(pointOutOfRange));
	}

	@Test
	public void findInRadiusTest_Failure_InvalidPoint() {
		expectedException.expect(ValidationException.class);
		service.findInRadius(new Point(null, BigDecimal.ZERO, null), new BigDecimal(10));
	}

	@Test
	public void findInRadiusTest_Failure_NegativeDistance() {
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage(testMessages.getString("msg.invalid.distance"));
		service.findInRadius(new Point("center", BigDecimal.ZERO, BigDecimal.ZERO), new BigDecimal("-1"));
	}

	@Test
	public void findInRadiusTest_Failure_NullDistance() {
		expectedException.expect(ValidationException.class);
		expectedException.expectMessage(testMessages.getString("msg.invalid.distance"));
		service.findInRadius(new Point("center", BigDecimal.ZERO, BigDecimal.ZERO), null);
	}

	@Test
	public void findAllTest_Success() {
		Mockito.when(repository.findAll()).thenReturn(Arrays.asList(mockPoint));
		Assert.assertFalse(service.findAll().isEmpty());
	}

	@Test
	public void findByIdTest_Failure_NullId() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(testMessages.getString("msg.point.id.required"));
		service.findById(null);
	}

	@Test
	public void findByIdTest_Success() {
		Mockito.when(repository.findOne(Mockito.anyLong())).thenReturn(mockPoint);
		Assert.assertEquals(service.findById(1L), mockPoint);
	}

	@Test
	public void deleteTest_Success() {
		service.delete(1L);
	}

	@Test
	public void deleteTest_Success_NullId() {
		service.delete(null);
	}

	@Test
	public void deleteTest_Failure_PointNotFound() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(testMessages.getString("msg.point.not.found"));
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).delete(1L);
		service.delete(1L);
	}

	@SuppressWarnings("unchecked")
	private Point buildInvalidPoint() {
		Set<ConstraintViolation<Point>> constraintViolation = Mockito.mock(Set.class);
		Point invalidPoint = new Point(null, null, null);
		Mockito.when(validator.validate(invalidPoint)).thenReturn(constraintViolation);
		return invalidPoint;
	}

	private void prepareMessageInMock(String messageKey) {
		Mockito.when(messageUtils.get(messageKey)).thenReturn(testMessages.getString(messageKey));
	}
}
