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
	 * @see IWorkOrderEventService#fireStatusChanged(IWorkOrder)
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
	 * @see IWorkOrderEventService#fireStatusChanged(IWorkOrder)
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
}