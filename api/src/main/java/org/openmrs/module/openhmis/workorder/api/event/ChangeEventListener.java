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
			WorkOrderStatus previousStatus = (WorkOrderStatus) event.getOldState()[4];
			WorkOrderStatus updatedStatus = (WorkOrderStatus) event.getState()[4];
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
