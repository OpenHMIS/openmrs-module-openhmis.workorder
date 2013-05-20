package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseCustomizableInstanceType;
import org.openmrs.module.openhmis.commons.api.entity.model.InstanceAttributeType;
import org.openmrs.module.openhmis.commons.api.entity.model.InstanceType;

public class WorkOrderType extends BaseCustomizableInstanceType<WorkOrderType>
		implements InstanceType<WorkOrderType> {

	@Override
	public InstanceAttributeType<WorkOrderType> getAttributeTypeInstance() {
		return new WorkOrderAttributeType();
	}
}
