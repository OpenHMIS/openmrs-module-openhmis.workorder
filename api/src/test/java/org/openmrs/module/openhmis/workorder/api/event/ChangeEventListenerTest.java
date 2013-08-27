/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.workorder.api.event;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderService;
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
	private IWorkOrderService dataService;
	private IWorkOrderEventService eventService;
	private StatusActionTester testAction;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Before
	public void before() throws Exception {
		executeDataSet(TestConstants.WORKORDER_DATASET);
		dataService = Context.getService(IWorkOrderService.class);
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