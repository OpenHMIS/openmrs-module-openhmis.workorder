package org.openmrs.module.openhmis.workorder.api.model;


import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.TestConstants;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class WorkOrderTest extends BaseModuleContextSensitiveTest {
	/**
	 * @throws Exception 
	 * @see WorkOrder#getRelatedObject()
	 * @verifies return a hydrated object when the class is Attributable
	 */
	@Before
	public void before() throws Exception {
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
	}
	
	@Test
	public void WorkOrder_shouldLoadAttributableAttributeType() throws Exception {
		executeDataSet(TestConstants.CORE_DATASET);
		IWorkOrderDataService service = Context.getService(IWorkOrderDataService.class);
		WorkOrder workOrder = service.getById(0);
		Collection<WorkOrderAttribute> attributes = workOrder.getAttributes();
		WorkOrderAttribute[] array = new WorkOrderAttribute[attributes.size()];
		attributes.toArray(array);
		Assert.assertTrue(array[0].getHydratedValue() instanceof Location);
	}
}