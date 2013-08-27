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

import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public class ChangeEventListener extends DefaultSaveOrUpdateEventListener
		implements PreInsertEventListener, PreUpdateEventListener {
	private static final long serialVersionUID = 531631953884575492L;
	
	/**
	 * Fire status change event if a WorkOrder's status is about to be updated
	 * 
	 * @should fire a change event if status changed
	 */
	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		if (event.getEntity() instanceof WorkOrder) {
			IWorkOrderEventService eventService = Context.getService(IWorkOrderEventService.class);
			// Using an integer index is a bit brittle, but seems to be the way to do it
			WorkOrderStatus previousStatus = (WorkOrderStatus) event.getOldState()[6];
			WorkOrderStatus updatedStatus = (WorkOrderStatus) event.getState()[6];
			if (previousStatus != updatedStatus)
				eventService.fireStatusChanged((WorkOrder) event.getEntity(), previousStatus);
		}
		return false;
	}

	/**
	 * Fire status change event for new WorkOrders.  If the new WorkOrder is
	 * being saved with a status other than NEW, fire with NEW first, then with
	 * the current status.
	 * 
	 * @should fire NEW first, then current
	 */
	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		if (event.getEntity() instanceof WorkOrder) {
			IWorkOrderEventService eventService = Context.getService(IWorkOrderEventService.class);
			WorkOrder workOrder = (WorkOrder) event.getEntity();
			WorkOrderStatus tempStatus = workOrder.getStatus();
			workOrder.setStatus(WorkOrderStatus.NEW);
			eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
			workOrder.setStatus(tempStatus);
			if (tempStatus != WorkOrderStatus.NEW)
				eventService.fireStatusChanged(workOrder, WorkOrderStatus.NEW);
		}
		return false;
	}
}
