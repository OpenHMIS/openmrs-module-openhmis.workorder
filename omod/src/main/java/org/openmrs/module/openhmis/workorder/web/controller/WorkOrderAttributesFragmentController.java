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
package org.openmrs.module.openhmis.workorder.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;
import org.openmrs.module.openhmis.workorder.web.WorkOrderWebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(WorkOrderWebConstants.WORKORDER_TYPE_ATTRIBUTE_FRAGMENT_URL)
public class WorkOrderAttributesFragmentController {
	@RequestMapping(method = RequestMethod.GET)
	public void paymentModeFragment(@RequestParam("uuid") String uuid, ModelMap model) {
		IWorkOrderTypeDataService service = Context.getService(IWorkOrderTypeDataService.class);
		WorkOrderType paymentMode = service.getByUuid(uuid);
		ConceptService conceptService = Context.getConceptService();
		Map<Integer, Concept> conceptMap = new HashMap<Integer, Concept>();
		for (WorkOrderAttributeType type : paymentMode.getAttributeTypes()) {
			if (type.getFormat().equals("org.openmrs.Concept") && type.getForeignKey() != null)
				conceptMap.put(type.getForeignKey(), conceptService.getConcept(type.getForeignKey()));
		}
		model.addAttribute("paymentMode", paymentMode);
		model.addAttribute("conceptMap", conceptMap);
	}
}
