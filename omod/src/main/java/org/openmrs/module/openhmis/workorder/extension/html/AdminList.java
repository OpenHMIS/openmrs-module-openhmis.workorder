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
