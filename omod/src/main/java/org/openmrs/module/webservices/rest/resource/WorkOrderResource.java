/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.webservices.rest.resource;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.entity.model.InstanceAttribute;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

import java.util.List;

@Resource(name = WorkOrderRestConstants.WORKORDER_RESOURCE, supportedClass=WorkOrder.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderResource extends BaseRestMetadataResource<WorkOrder> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("workOrders", Representation.REF);
		description.addProperty("workOrderType", Representation.REF);
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("attributes");
		}
		if (rep instanceof FullRepresentation) {
			description.addProperty("auditInfo", findMethod("getAuditInfo"));
		}
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = super.getCreatableProperties();
		description.addProperty("attributes");
		description.addProperty("workOrderType");
		return description;
	}
	
	@PropertySetter("attributes")
	public void setAttributes(WorkOrder instance, List<WorkOrderAttribute> attributes) {
		BaseRestDataResource.setCollection(instance, "attributes", InstanceAttribute.class, attributes);
		for (WorkOrderAttribute attr: instance.getAttributes())
			attr.setOwner(instance);
	}
	
	@Override
	public Class<? extends IMetadataDataService<WorkOrder>> getServiceClass() {
		return IWorkOrderDataService.class;
	}

	@Override
	public WorkOrder newDelegate() {
		return new WorkOrder();
	}

}
