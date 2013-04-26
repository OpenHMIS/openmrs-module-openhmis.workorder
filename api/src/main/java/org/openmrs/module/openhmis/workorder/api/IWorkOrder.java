package org.openmrs.module.openhmis.workorder.api;

import java.util.Collection;
import java.util.List;

import org.openmrs.OpenmrsMetadata;
import org.openmrs.User;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public interface IWorkOrder extends OpenmrsMetadata {

	public WorkOrderStatus getStatus();
	
	public void setStatus(WorkOrderStatus status);
	
	public User getAssignedTo();
	
	public void setAssignedTo(User user);
	
	public List<IWorkOrder> getChildOrders();
	
	public IWorkOrder getParentOrder();

	public void setParentOrder(IWorkOrder parentOrder);

	public Collection<WorkOrderAttribute> getAttributes();

	public Collection<WorkOrderAttribute> getActiveAttributes();

	public List<WorkOrderAttribute> getActiveAttributes(CustomValueDescriptor ofType);
}
