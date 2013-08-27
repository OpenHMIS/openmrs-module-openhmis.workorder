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
