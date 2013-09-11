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

import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public interface IWorkOrderService {
	/**
	 * Registers a custom javascript file for the specified {@link WorkOrderType}.
	 * @param type The work order type
	 * @param javascriptPath The path to the javascript file
	 * @should throw NullPointerException if type is null
	 * @should throw InvalidParameterException if javascript path is null
	 * @should throw InvalidParameterException if javascript path is empty
	 * @should register the javascript path only for the specified type
	 */
	public void registerModuleJavascript(WorkOrderType type, String javascriptPath);

	/**
	 * Gets the custom javascript file for the specified {@link WorkOrderType}.
	 * @param type The work order type
	 * @return The javascript file path or {@code null} if one has not been registered
	 * @should throw NullPointerException if type is null
	 * @should return null if no javascript file has been registered for the specified type
	 * @should return the javascript for only the specified type
	 */
	public String getModuleJavascript(WorkOrderType type);

	/**
	 * Gets the custom javascript file for the specified work order type.
	 * @param typeUuid The work order type UUID
	 * @return The javascript file path or {@code null} if one has not been registered
	 * @should throw InvalidParameterException if type uuid is null or empty
	 * @should return null if no javascript file has been registered for the specified type
	 * @should return the javascript for only the specified type
	 */
	public String getModuleJavascript(String typeUuid);
}

