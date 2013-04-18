package org.openmrs.module.openhmis.workorder.api.model;

public enum WorkOrderStatus {
	NEW(), IN_PROGRESS(), COMPLETE(), CANCELLED();

	private WorkOrderStatus() { }
}
