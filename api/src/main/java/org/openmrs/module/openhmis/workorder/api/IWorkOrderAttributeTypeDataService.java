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
package org.openmrs.module.openhmis.workorder.api;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

import javax.persistence.NonUniqueResultException;
import java.util.List;

public interface IWorkOrderAttributeTypeDataService extends IMetadataDataService<WorkOrderAttributeType> {
	/**
	 * Finds any {@link WorkOrderType} {@link WorkOrderAttributeType}s with the specified class.
	 * @param type The work order type
	 * @param attributeClass The attribute class
	 * @return A list of the matching attribute types
	 * @should throw NullPointerException if type is null
	 * @should throw NullPointerException if attributeClass is null
	 * @should return empty list when no results
	 * @should only return attributes for specified type
	 */
	public List<WorkOrderAttributeType> findByClass(WorkOrderType type, Class attributeClass);

	/**
	 * Gets the {@link WorkOrderType} {@link WorkOrderAttributeType} for the specified class.
	 * @param type The work order type.
	 * @param attributeClass The attribute class.
	 * @return The {@link WorkOrderAttributeType} or {@code null} if no attributes with the specified class can be found.
	 * @throws NonUniqueResultException
	 * @should throw NullPointerException if type is null
	 * @should throw NullPointerException if attributeClass is null
	 * @should throw NonUniqueResultException if the type has multiple attributes with the specified class
	 * @should return null if no attribute is found
	 * @should return the attribute type for the specified type
	 */
	public WorkOrderAttributeType getByClass(WorkOrderType type, Class attributeClass) throws NonUniqueResultException;
}
