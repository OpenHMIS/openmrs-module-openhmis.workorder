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

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.util.WorkOrderStatusAction;

public interface IWorkOrderEventService extends OpenmrsService {

	public void registerWorkOrderStatusHandler(WorkOrderStatusAction action);
	public void registerWorkOrderStatusHandler(String moduleId, WorkOrderStatusAction action);

	public void unregisterWorkOrderStatusHandler(WorkOrderStatusAction action);
	public void unregisterWorkOrderStatusHandler(String moduleId, WorkOrderStatusAction action);

	/**
	 * Fire actions registered for work order status change
	 * @param workOrder Work Order with changed status
	 * @should fire registered handlers
	 * @should not fire unregistered handlers
	 */
	public void fireStatusChanged(WorkOrder workOrder, WorkOrderStatus previousStatus);
}
