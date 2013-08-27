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
package org.openmrs.module.openhmis.workorder.api.util;

import java.util.Set;

import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

public class WorkOrderUtil {
	public static WorkOrderAttribute getAttributeByTypeName(WorkOrder workOrder, String typeName) {
		WorkOrderAttributeType type = Context.getService(IWorkOrderAttributeTypeDataService.class)
				.getByFormatUnique(typeName, workOrder.getWorkOrderType());
		Set<WorkOrderAttribute> attrs = workOrder.getActiveAttributes((CustomValueDescriptor) type);
		if (attrs.size() > 0)
			return attrs.toArray(new WorkOrderAttribute[1])[0];
		else
			return null;
	}
	
	public static void ensureWorkOrderType(WorkOrderType workOrderType, GlobalProperty typeUuidProperty) {
		AdministrationService adminService = Context.getAdministrationService();
		String uuid = adminService.getGlobalProperty(typeUuidProperty.getProperty());
		if (uuid == null) {
			IWorkOrderTypeDataService service = Context.getService(IWorkOrderTypeDataService.class);
			service.save(workOrderType);
			typeUuidProperty.setPropertyValue(workOrderType.getUuid());
			adminService.saveGlobalProperty(typeUuidProperty);
		}
	}
}
