package org.openmrs.module.webservices.rest.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;

@Handler(supports = WorkOrderAttribute.class, order = 0)
public class WorkOrderAttributeResource extends BaseRestMetadataResource<WorkOrderAttribute> {

	@Override
	public Class<? extends IMetadataDataService<WorkOrderAttribute>> getServiceClass() {
		return null;
	}

	@Override
	public WorkOrderAttribute newDelegate() {
		return new WorkOrderAttribute();
	}
}
