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

public interface IWorkOrderEventService extends OpenmrsService {

	/**
	 * Registers a {@link WorkOrderStatusAction} event handler.
	 * @param action The handler to register
	 * @should register the handler to receive status change events
	 * @should not fail if handler has already been added
	 */
	public void registerWorkOrderStatusHandler(WorkOrderStatusAction action);

	/**
	 * Unregisters a {@link WorkOrderStatusAction} event handler.
	 * @param action The handler to unregister
	 * @should unregister the handler
	 * @should not fail if the handler has not been registered
	 */
	public void unregisterWorkOrderStatusHandler(WorkOrderStatusAction action);

	/**
	 * Fires the status changed event for all handlers registered
	 * @param workOrder Work Order with changed status
	 * @should fire registered handlers
	 * @should not fire unregistered handlers
	 */
	public void fireStatusChanged(WorkOrder workOrder, WorkOrderStatus previousStatus);
}
