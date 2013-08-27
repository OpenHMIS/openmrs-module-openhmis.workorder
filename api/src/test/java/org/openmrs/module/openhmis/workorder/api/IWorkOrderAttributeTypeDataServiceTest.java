package org.openmrs.module.openhmis.workorder.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

public class IWorkOrderAttributeTypeDataServiceTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderAttributeTypeDataService service;
	
	@Before
	public void before() throws Exception {
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
		service = Context.getService(IWorkOrderAttributeTypeDataService.class);
	}

	/**
	 * @see IWorkOrderAttributeTypeDataService#getByFormat(String,Integer)
	 * @verifies filter based on data type and work order
	 */
	@Test
	public void getByFormat_shouldFilterBasedOnDataTypeAndWorkOrder()
			throws Exception {
		List<WorkOrderAttributeType> results = service.getByFormat("org.openmrs.Location", 0);
		Assert.assertEquals(1, results.size());
		Assert.assertEquals("Location", results.get(0).getName());
	}
	

}