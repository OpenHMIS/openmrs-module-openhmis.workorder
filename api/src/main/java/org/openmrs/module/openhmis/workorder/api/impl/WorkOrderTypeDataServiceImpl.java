package org.openmrs.module.openhmis.workorder.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public class WorkOrderTypeDataServiceImpl extends BaseMetadataDataServiceImpl<WorkOrderType>
		implements IWorkOrderTypeDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrderType object) throws APIException {
	}
}
