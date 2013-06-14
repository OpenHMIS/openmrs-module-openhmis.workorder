package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;

public interface IWorkOrderService extends IMetadataDataService<WorkOrder> {
	public void registerCustomWorkOrderTypeJsModule(String workOrderTypeUuid, String modulePath);
	public String getJsModulePathByWorkOrderTypeUuid(String workOrderTypeUuid);
}
