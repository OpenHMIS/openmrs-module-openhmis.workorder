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
