package org.openmrs.module.openhmis.workorder.api.event;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.TestConstants;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration()
@Transactional
public class ChangeEventListenerTest extends BaseModuleContextSensitiveTest {
	private IWorkOrderDataService dataService;
	private IWorkOrderEventService eventService;
	private StatusActionTester testAction;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Before
	public void before() throws Exception {
		executeDataSet(TestConstants.WORKORDER_DATASET);
		dataService = Context.getService(IWorkOrderDataService.class);
		eventService = Context.getService(IWorkOrderEventService.class);
		testAction = new StatusActionTester();
		eventService.registerWorkOrderStatusHandler(testAction);
	}
	
	/**
	 * @see ChangeEventListener#onPostInsert(PostInsertEvent)
	 * @verifies fire NEW first, then current
	 */
	@Test
	public void onPostInsert_shouldFireNEWFirstThenCurrent() throws Exception {
		WorkOrder workOrder = new WorkOrder();
		workOrder.setName("Test");
		workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
		workOrder.setCreator(Context.getAuthenticatedUser());
		workOrder.setDateCreated(new Date());
		dataService.save(workOrder);
		sessionFactory.getCurrentSession().flush();

		WorkOrderStatus latest = (WorkOrderStatus) testAction.getTestValue();
		WorkOrderStatus previous = (WorkOrderStatus) testAction.getTestValue(1);
		Assert.assertEquals(WorkOrderStatus.NEW, previous);
		Assert.assertEquals(WorkOrderStatus.IN_PROGRESS, latest);
	}

	/**
	 * @see ChangeEventListener#onPostUpdate(PostUpdateEvent)
	 * @verifies fire a change event if status changed
	 */
	@Test
	public void onPostUpdate_shouldFireAChangeEventIfStatusChanged() throws Exception {
		WorkOrder workOrder = dataService.getById(0);
		workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
		dataService.save(workOrder);
		sessionFactory.getCurrentSession().flush();
		
		Assert.assertEquals(WorkOrderStatus.IN_PROGRESS, testAction.getTestValue());
	}
}