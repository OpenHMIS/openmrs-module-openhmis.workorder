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
package org.openmrs.module.openhmis.workorder.extension.html;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.openhmis.commons.web.WebConstants;
import org.openmrs.module.openhmis.workorder.web.WorkOrderWebConstants;
import org.openmrs.module.web.extension.AdministrationSectionExt;

public class AdminList extends AdministrationSectionExt{

	@Override
	public String getTitle() {
		return "openhmis.workorder.title";
	}

	@Override
	public Map<String, String> getLinks() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put(WebConstants.formUrl(WorkOrderWebConstants.WORKORDER_PAGE),
				"openhmis.workorder.admin.workorders");
		map.put(WebConstants.formUrl(WorkOrderWebConstants.WORKORDER_TYPE_PAGE),
				"openhmis.workorder.admin.workordertypes");
		map.put(WebConstants.formUrl(WorkOrderWebConstants.WORKORDER_ATTRIBUTE_TYPE_PAGE),
				"openhmis.workorder.admin.workorderattributetypes");
		return map;
	}

}
