package org.openmrs.module.webservices.rest.web.controller;

import org.openmrs.module.webservices.rest.web.WorkOrderRestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + WorkOrderRestConstants.MODULE_REST_ROOT)
public class WorkOrderResourceController extends MainResourceController {
	@Override
	public String getNamespace() {
		return WorkOrderRestConstants.MODULE_REST_ROOT;
	}
}
