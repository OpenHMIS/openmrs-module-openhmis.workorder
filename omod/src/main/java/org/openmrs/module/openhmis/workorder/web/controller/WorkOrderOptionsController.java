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
