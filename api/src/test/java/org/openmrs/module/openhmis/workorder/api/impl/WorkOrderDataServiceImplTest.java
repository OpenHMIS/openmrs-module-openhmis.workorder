package org.openmrs.module.openhmis.workorder.api.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class WorkOrderDataServiceImplTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderService service;
	
	@Before
	public void before() {
		service = Context.getService(IWorkOrderService.class);
	}
	
	/**
	 * @see WorkOrderServiceImpl#save(WorkOrder)
	 * @verifies ensure that child work orders are ordered contiguously
	 */
	@Test
	public void save_shouldEnsureThatChildWorkOrdersAreOrderedContiguously() throws Exception {
		final String SUBORDER_1_NAME = "Sub Order 1";
		final String SUBORDER_2_NAME = "Sub Order 2";
		WorkOrder 	workOrder = new WorkOrder(),
					subOrder1 = new WorkOrder(),
					subOrder2 = new WorkOrder();
		workOrder.setName("Test Order");
		subOrder1.setName(SUBORDER_1_NAME);
		subOrder2.setName(SUBORDER_2_NAME);
		workOrder.addWorkOrder(subOrder1);
		workOrder.addWorkOrder(subOrder2);
		
		WorkOrder result = service.save(workOrder);
		subOrder1 = result.getWorkOrders().get(0);
		subOrder2 = result.getWorkOrders().get(1);
				
		Assert.assertEquals(SUBORDER_1_NAME, subOrder1.getName());
		Assert.assertEquals((Integer) 0, subOrder1.getItemOrder());
		Assert.assertEquals(SUBORDER_2_NAME, subOrder2.getName());
		Assert.assertEquals((Integer) 1, subOrder2.getItemOrder());
	}
}