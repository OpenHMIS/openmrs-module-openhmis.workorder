package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceType;

public class WorkOrderType extends BaseCustomizableInstanceType<WorkOrderType, WorkOrderAttributeType> {

	@Override
	public WorkOrderAttributeType getAttributeTypeInstance() {
		return new WorkOrderAttributeType();
	}
}
