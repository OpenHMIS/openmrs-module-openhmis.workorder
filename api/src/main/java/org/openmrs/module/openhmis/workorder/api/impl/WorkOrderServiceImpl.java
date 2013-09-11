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
package org.openmrs.module.openhmis.workorder.api.impl;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class WorkOrderServiceImpl implements IWorkOrderService {
	private static Map<String, String> workOrderTypeToJsModulePathMap = new HashMap<String, String>();

	@Override
	public void registerModuleJavascript(WorkOrderType type, String javascriptPath) {
		if (type == null) {
			throw new NullPointerException("The work order type must be defined.");
		}
		if (StringUtils.isEmpty(javascriptPath)) {
			throw new InvalidParameterException("The javascript path must be defined.");
		}

		workOrderTypeToJsModulePathMap.put(type.getUuid(), javascriptPath);
	}

	@Override
	public String getModuleJavascript(WorkOrderType type) {
		if (type == null) {
			throw new NullPointerException("The work order type must be defined.");
		}

		return getModuleJavascript(type.getUuid());
	}

	@Override
	public String getModuleJavascript(String typeUuid) {
		if (StringUtils.isEmpty(typeUuid)) {
			throw new InvalidParameterException("The work order type UUID must be defined.");
		}

		return workOrderTypeToJsModulePathMap.get(typeUuid);
	}
}

