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
package org.openmrs.module.openhmis.workorder.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.WorkOrderStatusAction;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

import java.util.HashSet;
import java.util.Set;

public class WorkOrderEventServiceImpl extends BaseOpenmrsService implements IWorkOrderEventService {
	private static Set<WorkOrderStatusAction> handlers = new HashSet<WorkOrderStatusAction>();

	@Override
	public void registerWorkOrderStatusHandler(WorkOrderStatusAction action) {
		if (!handlers.contains(action)) {
			handlers.add(action);
		}
	}

	@Override
	public void unregisterWorkOrderStatusHandler(WorkOrderStatusAction action) {
		handlers.remove(action);
	}

	@Override
	public void fireStatusChanged(WorkOrder workOrder, WorkOrderStatus previousStatus) {
		for (WorkOrderStatusAction action : handlers) {
			action.apply(workOrder, previousStatus);
		}
	}
}
