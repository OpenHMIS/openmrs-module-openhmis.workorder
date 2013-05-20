package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;

@Resource(name = WorkOrderRestConstants.WORKORDER_ATTRIBUTE_TYPE_RESOURCE, supportedClass=WorkOrderAttributeType.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderAttributeTypeResource extends
		BaseRestMetadataResource<WorkOrderAttributeType> {

	@Override
	public Class<? extends IMetadataDataService<WorkOrderAttributeType>> getServiceClass() {
		return IWorkOrderAttributeTypeDataService.class;
	}

	@Override
	public WorkOrderAttributeType newDelegate() {
		return new WorkOrderAttributeType();
	}

}
