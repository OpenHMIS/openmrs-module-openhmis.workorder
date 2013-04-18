package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.module.openhmis.workorder.api.util.IWorkOrderAction;

public interface IWorkOrderEventService {

	public void registerWorkOrderStatusHandler(IWorkOrderAction action);
	public void registerWorkOrderStatusHandler(String moduleId, IWorkOrderAction action);

	public void unregisterWorkOrderStatusHandler(IWorkOrderAction action);
	public void unregisterWorkOrderStatusHandler(String moduleId, IWorkOrderAction action);

	/**
	 * Fire actions registered for work order status change
	 * @param workOrder Work Order with changed status
	 * @should fire registered handlers
	 * @should not fire unregistered handlers
	 */
	public void fireStatusChanged(IWorkOrder workOrder);
}
