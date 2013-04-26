package org.openmrs.module.openhmis.workorder.api.model;

import java.util.List;

import org.openmrs.BaseCustomizableMetadata;
import org.openmrs.User;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.openhmis.workorder.api.IWorkOrder;

public class WorkOrder extends BaseCustomizableMetadata<WorkOrderAttribute> implements IWorkOrder, Customizable<WorkOrderAttribute> {

	private Integer workOrderId;
	private WorkOrderStatus status;
	private User assignedTo;
	private IWorkOrder parentOrder;
	
	/** Getters & setters **/
	@Override
	public Integer getId() {
		return workOrderId;
	}

	@Override
	public void setId(Integer id) {
		workOrderId = id;
	}

	@Override
	public WorkOrderStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(WorkOrderStatus status) {
		this.status = status;
	}

	@Override
	public User getAssignedTo() {
		return assignedTo;
	}

	@Override
	public void setAssignedTo(User user) {
		this.assignedTo = user;
	}

	@Override
	public List<IWorkOrder> getChildOrders() {
		return null;
	}

	@Override
	public IWorkOrder getParentOrder() {
		return parentOrder;
	}

	@Override
	public void setParentOrder(IWorkOrder parent) {
		parentOrder = parent;
	}
}
