package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.attribute.Attribute;
import org.openmrs.module.openhmis.commons.attribute.HydrateableAttribute;


public class WorkOrderAttribute extends HydrateableAttribute<WorkOrderAttributeType, WorkOrder> implements Attribute<WorkOrderAttributeType, WorkOrder> {

	private Integer workOrderAttributeId;
	
	@Override
	public Integer getId() {
		return workOrderAttributeId;
	}

	@Override
	public void setId(Integer id) {
		workOrderAttributeId = id;
	}
}
