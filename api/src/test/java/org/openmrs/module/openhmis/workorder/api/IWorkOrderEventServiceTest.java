package org.openmrs.module.openhmis.workorder.api;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.util.IWorkOrderAction;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class IWorkOrderEventServiceTest extends BaseModuleContextSensitiveTest {

	private IWorkOrderEventService eventService;
	
	private class ActionTester implements IWorkOrderAction {
		private Object testValue = null;
		public Object getTestValue() { return testValue; }
		public void setTestValue(Object value) { testValue = value; }
		public void apply(IWorkOrder workOrder) {
			setTestValue(workOrder);
		}
	}

	@Before
	public void before() {
		eventService = Context.getService(IWorkOrderEventService.class);
	}

	/**
	 * @see IWorkOrderEventService#fireStatusChanged(IWorkOrder)
	 * @verifies fire registered handlers
	 */
	@Test
	public void fireStatusChanged_shouldFireRegisteredHandlers() throws Exception {
		ActionTester testAction = new ActionTester();
		eventService.registerWorkOrderStatusHandler(testAction);
		IWorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder);
		Assert.assertEquals(workOrder, testAction.getTestValue());
	}

	/**
	 * @see IWorkOrderEventService#fireStatusChanged(IWorkOrder)
	 * @verifies not fire unregistered handlers
	 */
	@Test
	public void fireStatusChanged_shouldNotFireUnregisteredHandlers() throws Exception {
		ActionTester testAction = new ActionTester();
		eventService.registerWorkOrderStatusHandler(testAction);
		eventService.unregisterWorkOrderStatusHandler(testAction);
		IWorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder);
		Assert.assertNull(testAction.getTestValue());		
	}
}