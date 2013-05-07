package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.Attributable;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;

public interface IWorkOrderAttributeTypeDataService
		extends IMetadataDataService<WorkOrderAttributeType> {
	
	/**
	 * Convert an object of type Attributable to a WorkOrderAttribute by
	 * searching for a WorkOrderAttributeType to handle the class in
	 * question.
	 * @param value an object to store as a work order attribute
	 * @return WorkOrderAttribute with value set, or null if no type can be
	 * 		found to handle the object class
	 * @should return a set WorkOrderAttribute
	 * @should throw an exception if value is null
	 * @should throw an exception if no WorkOrderAttributeType is found
	 */
	public <T extends Attributable<?>> WorkOrderAttribute convertToAttribute(T value);
	
	public WorkOrderAttributeType getByDatatypeClassname(String datatypeClassname);
}
