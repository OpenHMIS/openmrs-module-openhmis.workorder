/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
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

import java.util.List;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.entity.model.InstanceAttributeType;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

@Resource(name = WorkOrderRestConstants.WORKORDER_TYPE_RESOURCE, supportedClass=WorkOrderType.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderTypeResource extends
		BaseRestMetadataResource<WorkOrderType> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("description");
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("attributeTypes");
		}
		if (rep instanceof FullRepresentation) {
			description.addProperty("auditInfo", findMethod("getAuditInfo"));
		}
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = super.getCreatableProperties();
		description.addProperty("attributeTypes");
		return description;
	}
	
	@PropertySetter("attributeTypes")
	public void setAttributeTypes(WorkOrderType instance, List<WorkOrderAttributeType> attributeTypes) {
		BaseRestDataResource.setCollection(instance, "attributeTypes", InstanceAttributeType.class, attributeTypes);
		for (WorkOrderAttributeType type: instance.getAttributeTypes())
			type.setOwner(instance);
	}
	
	@Override
	public Class<? extends IMetadataDataService<WorkOrderType>> getServiceClass() {
		return IWorkOrderTypeDataService.class;
	}

	@Override
	public WorkOrderType newDelegate() {
		return new WorkOrderType();
	}

}
