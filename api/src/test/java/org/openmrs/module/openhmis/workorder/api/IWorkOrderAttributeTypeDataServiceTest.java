package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class IWorkOrderAttributeTypeDataServiceTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderAttributeTypeDataService service;
	
	@Before
	public void before() throws Exception {
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
		service = Context.getService(IWorkOrderAttributeTypeDataService.class);
	}
	
	/**
	 * @see IWorkOrderAttributeTypeDataService#convertToAttribute(Attributable)
	 * @verifies return a set WorkOrderAttribute
	 */
	@Test
	public void convertToAttribute_shouldReturnASetWorkOrderAttribute()
			throws Exception {
		Integer locationId = 0;
		Location location = new Location(locationId);
		WorkOrderAttribute attribute = service.convertToAttribute(location);
		Assert.assertEquals(locationId.toString(), attribute.getValueReference());
	}

	/**
	 * @see IWorkOrderAttributeTypeDataService#convertToAttribute(Attributable)
	 * @verifies throw an exception if value is null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void convertToAttribute_shouldThrowAnExceptionIfValueIsNull() throws Exception {
		service.convertToAttribute(null);
	}

	/**
	 * @see IWorkOrderAttributeTypeDataService#convertToAttribute(T)
	 * @verifies throw an exception if no WorkOrderAttributeType is found
	 */
	@Test(expected = APIException.class)
	public void convertToAttribute_shouldThrowAnExceptionIfNoWorkOrderAttributeTypeIsFound() throws Exception {
		Concept concept = new Concept(0);
		service.convertToAttribute(concept);
	}
}