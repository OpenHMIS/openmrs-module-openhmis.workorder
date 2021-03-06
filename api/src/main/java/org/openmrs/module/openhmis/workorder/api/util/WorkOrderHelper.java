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
package org.openmrs.module.openhmis.workorder.api.util;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderAttributeTypeDataService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderTypeDataService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttribute;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderAttributeType;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderType;

import java.util.Set;

public class WorkOrderHelper {
	public static WorkOrderAttribute getAttributeByType(WorkOrder workOrder, Class clazz) {
		IWorkOrderAttributeTypeDataService service = Context.getService(IWorkOrderAttributeTypeDataService.class);

		WorkOrderAttributeType type = service.getByClass(workOrder.getInstanceType(), clazz);
		Set<WorkOrderAttribute> attrs = workOrder.getActiveAttributes(type);

		return attrs.size() > 0 ? (WorkOrderAttribute)CollectionUtils.get(attrs, 0) : null;
	}

	public static <T> T getAttributeTypeValue(WorkOrder workOrder, Class clazz) {
		return (T) getAttributeByType(workOrder, clazz).getHydratedValue();
	}

	/**
	 * Checks to see if a {@link WorkOrderType} with the UUID in the specified {@link GlobalProperty} exists.
	 * @param typeUuidProperty The {@link GlobalProperty} where the UUID is stored.
	 * @return {@code true} if the global property and work order type exist; otherwise, {@code false}.
	 */
	public static boolean checkWorkOrderType(GlobalProperty typeUuidProperty) {
		boolean result = false;

		AdministrationService adminService = Context.getAdministrationService();
		String uuid = adminService.getGlobalProperty(typeUuidProperty.getProperty());

		if (uuid != null) {
			IWorkOrderTypeDataService service = Context.getService(IWorkOrderTypeDataService.class);
			WorkOrderType type = service.getByUuid(uuid);

			result = type != null;
		}

		return result;
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
