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

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public class IWorkOrderTypeDataServiceTest extends IMetadataDataServiceTest<IWorkOrderTypeDataService, WorkOrderType> {
	@Override
	public void before() throws Exception {
		super.before();
		executeDataSet(IWorkOrderDataServiceTest.DATASET);
	}
	@Override
	protected WorkOrderType createEntity(boolean makeValid) {
		WorkOrderType workOrderType = new WorkOrderType();
		
		if (makeValid)
			workOrderType.setName("Test Work Order Type");
		workOrderType.setDescription("Work order type description");
		workOrderType.addAttributeType("Test attr. type", "java.lang.String", null, null, false, null);
		return workOrderType;
	}

	@Override
	protected int getTestEntityCount() {
		return 1;
	}

	@Override
	protected void updateEntityFields(WorkOrderType workOrder) {
		workOrder.setName("Different name");
		workOrder.setDescription("Different description");
		workOrder.addAttributeType("Another test", "java.lang.Boolean", null, null, false, null);
	}
}
