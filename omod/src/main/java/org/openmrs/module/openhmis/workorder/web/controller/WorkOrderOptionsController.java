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
package org.openmrs.module.openhmis.workorder.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderService;
import org.openmrs.module.openhmis.workorder.web.WorkOrderWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(WorkOrderWebConstants.WORKORDER_OPTIONS_URI)
public class WorkOrderOptionsController {
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String getModuleNamespace(
			@RequestParam(value = "jsModuleForWorkOrderType", required = true) String workOrderTypeUuid) {
		return Context.getService(IWorkOrderService.class).getJsModulePathByWorkOrderTypeUuid(workOrderTypeUuid);
	}
}
