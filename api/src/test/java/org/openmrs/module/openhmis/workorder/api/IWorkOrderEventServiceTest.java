package org.openmrs.module.openhmis.workorder.api;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.event.StatusActionTester;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class IWorkOrderEventServiceTest extends BaseModuleContextSensitiveTest {

	private IWorkOrderEventService eventService;

	@Before
	public void before() {
		eventService = Context.getService(IWorkOrderEventService.class);
	}

	/**
	 * @see IWorkOrderEventService#fireStatusChanged(WorkOrder, WorkOrderStatus)
	 * @verifies fire registered handlers
	 */
	@Test
	public void fireStatusChanged_shouldFireRegisteredHandlers() throws Exception {
		StatusActionTester testAction = new StatusActionTester();
		eventService.registerWorkOrderStatusHandler(testAction);
		WorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		Assert.assertEquals(WorkOrderStatus.NEW, testAction.getTestValue());
	}

	/**
	 * @see IWorkOrderEventService#fireStatusChanged(WorkOrder, WorkOrderStatus)
	 * @verifies not fire unregistered handlers
	 */
	@Test
	public void fireStatusChanged_shouldNotFireUnregisteredHandlers() throws Exception {
		StatusActionTester testAction = new StatusActionTester();
		eventService.registerWorkOrderStatusHandler(testAction);
		eventService.unregisterWorkOrderStatusHandler(testAction);
		WorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		Assert.assertNull(testAction.getTestValue());		
	}

	/**
	 * @verifies register the handler to receive status change events
	 * @see IWorkOrderEventService#registerWorkOrderStatusHandler(WorkOrderStatusAction)
	 */
	@Test
	public void registerWorkOrderStatusHandler_shouldRegisterTheHandlerToReceiveStatusChangeEvents() throws Exception {
		StatusActionTester tester = new StatusActionTester();
		eventService.registerWorkOrderStatusHandler(tester);

		WorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		Assert.assertEquals(WorkOrderStatus.NEW, tester.getTestValue());
	}

	/**
	 * @verifies not fail if handler has already been added
	 * @see IWorkOrderEventService#registerWorkOrderStatusHandler(WorkOrderStatusAction)
	 */
	@Test
	public void registerWorkOrderStatusHandler_shouldNotFailIfHandlerHasAlreadyBeenAdded() throws Exception {
		StatusActionTester tester = new StatusActionTester();

		eventService.registerWorkOrderStatusHandler(tester);
		eventService.registerWorkOrderStatusHandler(tester);

		WorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		Assert.assertEquals(WorkOrderStatus.NEW, tester.getTestValue());
	}

	/**
	 * @verifies unregister the handler
	 * @see IWorkOrderEventService#unregisterWorkOrderStatusHandler(WorkOrderStatusAction)
	 */
	@Test
	public void unregisterWorkOrderStatusHandler_shouldUnregisterTheHandler() throws Exception {
		StatusActionTester tester = new StatusActionTester();

		eventService.registerWorkOrderStatusHandler(tester);
		eventService.unregisterWorkOrderStatusHandler(tester);

		WorkOrder workOrder = new WorkOrder();
		eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		Assert.assertNull(tester.getTestValue());
	}

	/**
	 * @verifies not fail if the handler has not been registered
	 * @see IWorkOrderEventService#unregisterWorkOrderStatusHandler(WorkOrderStatusAction)
	 */
	@Test
	public void unregisterWorkOrderStatusHandler_shouldNotFailIfTheHandlerHasNotBeenRegistered() throws Exception {
		StatusActionTester tester = new StatusActionTester();
		eventService.unregisterWorkOrderStatusHandler(tester);
	}
}