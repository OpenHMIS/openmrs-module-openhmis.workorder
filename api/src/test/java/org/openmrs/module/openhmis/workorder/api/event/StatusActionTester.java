package org.openmrs.module.openhmis.workorder.api.event;

import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.WorkOrderStatusAction;

import java.util.LinkedList;

public class StatusActionTester implements WorkOrderStatusAction {
	private LinkedList<Object> values;
	public StatusActionTester() {
		values = new LinkedList<Object>();
		for (int i = 0; i < 10; i++)
			values.add(null);
	}
	public Object getTestValue() { return values.peek(); }
	public Object getTestValue(int index) { return values.get(index); }
	public void setTestValue(Object value) {
		values.remove(9);
		values.addFirst(value);
	}
	@Override
	public void apply(WorkOrder workOrder, WorkOrderStatus previousStatus) {
		setTestValue(workOrder.getStatus());
	}
}
