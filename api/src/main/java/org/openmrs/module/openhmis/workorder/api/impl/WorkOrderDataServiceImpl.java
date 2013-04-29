package org.openmrs.module.openhmis.workorder.api.impl;

import java.util.Collection;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

public class WorkOrderDataServiceImpl
		extends BaseMetadataDataServiceImpl<WorkOrder>
		implements IWorkOrderDataService {

	@Override
	protected Collection<? extends OpenmrsObject> getRelatedObjects(WorkOrder entity) {
		return entity.getWorkOrders();
	}
	
	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrder object) throws APIException {
		if ((object.getStatus() == WorkOrderStatus.COMPLETE || object.getStatus() == WorkOrderStatus.CANCELLED)
				&& object.getAssignedTo() == null)
			throw new APIException("A work order must be assigned to a user to be saved as " + object.getStatus());
	}
}
