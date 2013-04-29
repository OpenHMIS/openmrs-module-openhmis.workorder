package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.util.WorkOrderAction;

public interface IWorkOrderEventService {

	public void registerWorkOrderStatusHandler(WorkOrderAction action);
	public void registerWorkOrderStatusHandler(String moduleId, WorkOrderAction action);

	public void unregisterWorkOrderStatusHandler(WorkOrderAction action);
	public void unregisterWorkOrderStatusHandler(String moduleId, WorkOrderAction action);

	/**
	 * Fire actions registered for work order status change
	 * @param workOrder Work Order with changed status
	 * @should fire registered handlers
	 * @should not fire unregistered handlers
	 */
	public void fireStatusChanged(WorkOrder workOrder);
}
