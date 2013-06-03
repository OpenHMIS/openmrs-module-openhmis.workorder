package org.openmrs.module.openhmis.workorder.api;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public interface IWorkOrderAttributeTypeDataService
		extends IMetadataDataService<WorkOrderAttributeType> {
	
	/**
	 * 
	 * @param datatypeClassname class name of the attribute type
	 * @param workOrderTypeId the work order type
	 * @return a list of matching attribute types
	 * @should filter based on data type and work order
	 */
	public List<WorkOrderAttributeType> getByFormat(String datatypeClassname, Integer workOrderTypeId);
	public List<WorkOrderAttributeType> getByFormat(String datatypeClassname, WorkOrderType workOrderType);

	public WorkOrderAttributeType getByFormatUnique(String datatypeClassname, Integer workOrderTypeId) throws NonUniqueResultException;
	public WorkOrderAttributeType getByFormatUnique(String datatypeClassname, WorkOrderType workOrderType) throws NonUniqueResultException;
}
