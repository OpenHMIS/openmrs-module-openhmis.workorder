package org.openmrs.module.webservices.rest.resource;

import java.util.List;

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

@Resource(name = WorkOrderRestConstants.WORKORDER_RESOURCE, supportedClass=WorkOrder.class, supportedOpenmrsVersions={"1.9"})
public class WorkOrderResource extends BaseRestMetadataResource<WorkOrder> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
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
