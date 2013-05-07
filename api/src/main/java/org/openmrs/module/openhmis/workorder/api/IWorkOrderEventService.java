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
