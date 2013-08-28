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
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.commons.api.entity.model.InstanceAttributeType;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@Resource(name = WorkOrderRestConstants.WORKORDER_ATTRIBUTE_TYPE_RESOURCE, supportedClass = WorkOrderAttributeType.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
@Handler(supports = WorkOrderAttributeType.class)
public class WorkOrderAttributeTypeResource
		extends BaseRestMetadataResource<WorkOrderAttributeType>
		implements DelegatingSubclassHandler<InstanceAttributeType, WorkOrderAttributeType>,
			org.openmrs.module.webservices.rest.web.resource.api.Resource, Converter<WorkOrderAttributeType> {
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("attributeOrder");
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("format");
			description.addProperty("foreignKey");
			description.addProperty("regExp");
			description.addProperty("required");
			description.addProperty("retired");
		}
		return description;
	}

	@Override
	public WorkOrderAttributeType newDelegate() {
		return new WorkOrderAttributeType();
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties()
			throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("name");
		description.addProperty("description");
		return description;
	}

	@Override
	public String getTypeName() {
		return WorkOrderAttributeType.class.getSimpleName();
	}

	@Override
	public PageableResult getAllByType(RequestContext context)
			throws ResourceDoesNotSupportOperationException {
		PagingInfo info = PagingUtil.getPagingInfoFromContext(context);
		return new AlreadyPaged<WorkOrderAttributeType>(
			context,
			Context.getService(IWorkOrderAttributeTypeDataService.class).getAll(info),
			info.hasMoreResults()
		);
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		if (context.getType().equals(getTypeName())) {
			return getAllByType(context);
		}
		else throw new ResourceDoesNotSupportOperationException();
	}
	
	@PropertyGetter("retired")
	public Boolean getRetired(WorkOrderAttributeType instance) {
		return instance.isRetired();
	}

//	@Override
//	public WorkOrderAttributeType newInstance(String type) {
//		return new WorkOrderAttributeType();
//	}

	@Override
	public Class<? extends IMetadataDataService<WorkOrderAttributeType>> getServiceClass() {
		return IWorkOrderAttributeTypeDataService.class; 
	}

	@Override
	public Class<InstanceAttributeType> getSuperclass() {
		return InstanceAttributeType.class;
		//return ReflectionUtil.getParameterizedTypeFromInterface(getClass(), DelegatingSubclassHandler.class, 0);
	}

	@Override
	public Class<WorkOrderAttributeType> getSubclassHandled() {
		return WorkOrderAttributeType.class;
	}
}
