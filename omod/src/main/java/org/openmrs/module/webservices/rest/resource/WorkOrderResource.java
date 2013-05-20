package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;

@Resource(name = WorkOrderRestConstants.WORKORDER_RESOURCE, supportedClass=WorkOrder.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderResource extends BaseRestMetadataResource<WorkOrder> {

	@Override
	public Class<? extends IMetadataDataService<WorkOrder>> getServiceClass() {
		return IWorkOrderDataService.class;
	}

	@Override
	public WorkOrder newDelegate() {
		return new WorkOrder();
	}

}
