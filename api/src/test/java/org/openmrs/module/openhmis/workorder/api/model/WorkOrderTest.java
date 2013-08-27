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
package org.openmrs.module.openhmis.workorder.api.model;


import java.util.Collection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderService;
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
		IWorkOrderService service = Context.getService(IWorkOrderService.class);
		WorkOrder workOrder = service.getById(0);
		Collection<WorkOrderAttribute> attributes = workOrder.getAttributes();
		WorkOrderAttribute[] array = new WorkOrderAttribute[attributes.size()];
		attributes.toArray(array);
		Assert.assertTrue(array[0].getHydratedValue() instanceof Location);
	}
}