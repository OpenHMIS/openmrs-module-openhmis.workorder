package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;

@Resource(name = WorkOrderRestConstants.WORKORDER_TYPE_RESOURCE, supportedClass=WorkOrderType.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderTypeResource extends
		BaseRestMetadataResource<WorkOrderType> {

	@Override
	public Class<? extends IMetadataDataService<WorkOrderType>> getServiceClass() {
		return IWorkOrderTypeDataService.class;
	}

	@Override
	public WorkOrderType newDelegate() {
		return new WorkOrderType();
	}

}
