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
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public class IWorkOrderDataServiceTest extends IMetadataDataServiceTest<IWorkOrderService, WorkOrder> {
	public static final String DATASET = TestConstants.BASE_DATASET_DIR + "WorkOrderTest.xml";
	@Override
	public void before() throws Exception {
		super.before();
		executeDataSet(DATASET);
	}
	
	@Override
	protected WorkOrder createEntity(boolean valid) {
		WorkOrder workOrder = new WorkOrder();
		if (valid)
			workOrder.setName("Test work order");
		
		workOrder.setDescription("A work order to test.");
		workOrder.setStatus(WorkOrderStatus.NEW);
		return workOrder;
	}

	@Override
	protected int getTestEntityCount() {
		return 1;
	}

	@Override
	protected void updateEntityFields(WorkOrder entity) {
		entity.setName(entity.getName() + " updated");
		entity.setDescription(entity.getDescription() + " updated");
		entity.setStatus(entity.getStatus() == WorkOrderStatus.IN_PROGRESS ? WorkOrderStatus.NEW : WorkOrderStatus.IN_PROGRESS);
	}

	@Override
	protected void assertEntity(WorkOrder expected, WorkOrder actual) {
		super.assertEntity(expected, actual);
		Assert.assertEquals(expected.getStatus(), actual.getStatus());
		Assert.assertEquals(expected.getAssignedTo(), actual.getAssignedTo());
	}
}
