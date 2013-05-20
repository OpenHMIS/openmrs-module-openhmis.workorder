package org.openmrs.module.openhmis.workorder.web.controller;

import org.openmrs.module.openhmis.workorder.web.WorkOrderWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(WorkOrderWebConstants.WORKORDER_TYPE_PAGE)
public class WorkOrderTypesController {
	@RequestMapping(method = RequestMethod.GET)
	public void workorderTypes(ModelMap model) {
	}
}
