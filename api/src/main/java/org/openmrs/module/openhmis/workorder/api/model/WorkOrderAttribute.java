package org.openmrs.module.openhmis.workorder.api.model;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseMetadataInstanceAttribute;
import org.openmrs.module.openhmis.commons.attribute.AttributeUtil;


public class WorkOrderAttribute extends BaseMetadataInstanceAttribute<WorkOrder, WorkOrderAttributeType> {

	private Integer workOrderAttributeId;
	
	@Override
	public void setAttributeType(WorkOrderAttributeType attributeType) {
		super.setAttributeType(attributeType);
	}
	
	@Override
	public Integer getId() {
		return workOrderAttributeId;
	}

	@Override
	public void setId(Integer id) {
		workOrderAttributeId = id;
	}
	
	public Object getHydratedValue() {
		return AttributeUtil.tryToHydrateObject(getAttributeType().getFormat(), getValue());
	}
}
