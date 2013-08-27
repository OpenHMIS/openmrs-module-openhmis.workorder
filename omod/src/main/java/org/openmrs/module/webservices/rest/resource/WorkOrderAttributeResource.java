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

import org.openmrs.annotation.Handler;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

@Handler(supports = WorkOrderAttribute.class, order = 0)
public class WorkOrderAttributeResource extends BaseRestMetadataResource<WorkOrderAttribute> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("value");
			description.addProperty("attributeType", Representation.REF);
		}
		return description;
	}
	
	@PropertyGetter("value")
	public Object getPropertyValue(WorkOrderAttribute instance) {
		return instance.getHydratedValue();
	}
	
	@Override
	public Class<? extends IMetadataDataService<WorkOrderAttribute>> getServiceClass() {
		return null;
	}

	@Override
	public WorkOrderAttribute newDelegate() {
		return new WorkOrderAttribute();
	}
}
