package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.attribute.AttributeType;
import org.openmrs.attribute.BaseAttributeType;

public class WorkOrderAttributeType extends BaseAttributeType<WorkOrder> implements AttributeType<WorkOrder> {

	private Integer workOrderAttributeTypeId;
	@Override
	public Integer getId() {
		return workOrderAttributeTypeId;
	}

	@Override
	public void setId(Integer id) {
		workOrderAttributeTypeId = id;
	}
}
