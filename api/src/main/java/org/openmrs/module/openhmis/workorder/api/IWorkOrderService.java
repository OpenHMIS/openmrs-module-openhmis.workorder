package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;

public class IWorkOrderService extends BaseMetadataDataServiceImpl<IWorkOrder> {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(IWorkOrder object) throws APIException {
	}

}
