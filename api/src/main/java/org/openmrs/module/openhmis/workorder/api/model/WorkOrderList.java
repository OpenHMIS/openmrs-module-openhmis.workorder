package org.openmrs.module.openhmis.workorder.api.model;

import java.util.LinkedList;
import java.util.List;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.module.openhmis.workorder.api.IWorkOrder;

public class WorkOrderList extends BaseOpenmrsObject {
	private Integer workOrderGroupId;
	private List<IWorkOrder> workOrders;
	
	@Override
	public Integer getId() {
		return workOrderGroupId;
	}

	@Override
	public void setId(Integer id) {
		workOrderGroupId = id;
	}
	
	public void addWorkOrder(IWorkOrder workOrder) {
		if (workOrders == null)
			workOrders = new LinkedList<IWorkOrder>();
		workOrders.add(workOrder);
	}
	
	public boolean removeWorkOrder(IWorkOrder workOrder) {
		if (workOrders != null)
			return workOrders.remove(workOrder);
		return false;
	}

}
