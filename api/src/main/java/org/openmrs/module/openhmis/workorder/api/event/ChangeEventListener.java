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

	private IWorkOrderEventService eventService;

	public ChangeEventListener() {	}

	/**
	 * Fire status change event if a WorkOrder's status is about to be updated
	 * @param event The update event information.
	 * @return True if the event should be vetoed
	 * @should fire a change event if status changed
	 * @should not fire a change event if the status has not changed
	 */
	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		if (event.getEntity() instanceof WorkOrder) {
			ensureEvenService();

			// Using an integer index is a bit brittle, but seems to be the way to do it
			WorkOrderStatus previousStatus = (WorkOrderStatus) event.getOldState()[6];
			WorkOrderStatus updatedStatus = (WorkOrderStatus) event.getState()[6];

			if (previousStatus != updatedStatus) {
				eventService.fireStatusChanged((WorkOrder) event.getEntity(), previousStatus);
			}
		}

		return false;
	}

	/**
	 * Fire status change event for new {@link WorkOrder}s.  If the new WorkOrder is
	 * being saved with a status other than NEW, fire with NEW first, then with
	 * the current status.
	 * @param event The insert event information
	 * @return True if the event should be vetoed
	 * @should fire update status event for NEW status first then for current status
	 * @should fire update status event for new work orders
	 */
	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		if (event.getEntity() instanceof WorkOrder) {
			ensureEvenService();

			WorkOrder workOrder = (WorkOrder) event.getEntity();
			WorkOrderStatus tempStatus = workOrder.getStatus();

			if (tempStatus != WorkOrderStatus.NEW) {
				// If the status is something other than new, set status to new, raise the event,
				// update the status, and then raise the event again
				workOrder.setStatus(WorkOrderStatus.NEW);
				eventService.fireStatusChanged(workOrder, null);

				workOrder.setStatus(tempStatus);
				eventService.fireStatusChanged(workOrder, tempStatus);
			} else {
				eventService.fireStatusChanged(workOrder, null);
			}
		}

		return false;
	}

	private void ensureEvenService() {
		if (eventService == null) {
			eventService = Context.getService(IWorkOrderEventService.class);
		}
	}
}
