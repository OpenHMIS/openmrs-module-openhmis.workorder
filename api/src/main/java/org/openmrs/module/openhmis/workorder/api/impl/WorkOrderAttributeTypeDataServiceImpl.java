package org.openmrs.module.openhmis.workorder.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;

public class WorkOrderAttributeTypeDataServiceImpl extends
		BaseMetadataDataServiceImpl<WorkOrderAttributeType> implements
		IWorkOrderAttributeTypeDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(WorkOrderAttributeType object) throws APIException {
	}

}
