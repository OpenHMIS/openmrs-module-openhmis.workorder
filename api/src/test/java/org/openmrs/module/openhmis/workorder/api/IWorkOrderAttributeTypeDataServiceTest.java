package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

public class IWorkOrderAttributeTypeDataServiceTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderAttributeTypeDataService service;
	private IWorkOrderTypeDataService typeDataService;

	@Before
	public void before() throws Exception {
		executeDataSet(IWorkOrderDataServiceTest.DATASET);

		typeDataService = Context.getService(IWorkOrderTypeDataService.class);
		service = Context.getService(IWorkOrderAttributeTypeDataService.class);
	}

	/**
	 * @verifies throw NullPointerException if type is null
	 * @see IWorkOrderAttributeTypeDataService#findByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test(expected = NullPointerException.class)
	public void findByClass_shouldThrowNullPointerExceptionIfTypeIsNull() throws Exception {
		service.findByClass(null, Location.class);
	}

	/**
	 * @verifies throw NullPointerException if attributeClass is null
	 * @see IWorkOrderAttributeTypeDataService#findByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void findByClass_shouldThrowNullPointerExceptionIfAttributeClassIsNull() throws Exception {
		WorkOrderType type = typeDataService.getById(0);

		service.findByClass(type, null);
	}

	/**
	 * @verifies return empty list when no results
	 * @see IWorkOrderAttributeTypeDataService#findByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void findByClass_shouldReturnEmptyListWhenNoResults() throws Exception {
		WorkOrderType type = typeDataService.getById(0);

		List<WorkOrderAttributeType> results = service.findByClass(type, TestConstants.class);

		Assert.assertNotNull(results);
		Assert.assertEquals(0, results.size());
	}

	/**
	 * @verifies only return attributes for specified type
	 * @see IWorkOrderAttributeTypeDataService#findByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void findByClass_shouldOnlyReturnAttributesForSpecifiedType() throws Exception {
		WorkOrderType type = typeDataService.getById(0);

		List<WorkOrderAttributeType> results = service.findByClass(type, Location.class);

		Assert.assertNotNull(results);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("Location", results.get(0).getName());
	}

	/**
	 * @verifies throw NullPointerException if type is null
	 * @see IWorkOrderAttributeTypeDataService#getByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void getByClass_shouldThrowNullPointerExceptionIfTypeIsNull() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies throw NullPointerException if attributeClass is null
	 * @see IWorkOrderAttributeTypeDataService#getByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void getByClass_shouldThrowNullPointerExceptionIfAttributeClassIsNull() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies throw NonUniqueResultException if the type has multiple attributes with the specified class
	 * @see IWorkOrderAttributeTypeDataService#getByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void getByClass_shouldThrowNonUniqueResultExceptionIfTheTypeHasMultipleAttributesWithTheSpecifiedClass() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return null if no attribute is found
	 * @see IWorkOrderAttributeTypeDataService#getByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void getByClass_shouldReturnNullIfNoAttributeIsFound() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}

	/**
	 * @verifies return the attribute type for the specified type
	 * @see IWorkOrderAttributeTypeDataService#getByClass(org.openmrs.module.openhmis.workorder.api.model.WorkOrderType, Class)
	 */
	@Test
	public void getByClass_shouldReturnTheAttributeTypeForTheSpecifiedType() throws Exception {
		//TODO auto-generated
		Assert.fail("Not yet implemented");
	}
}