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

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.WorkOrderStatusAction;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkOrderEventServiceImpl extends BaseOpenmrsService implements IWorkOrderEventService {
	
	private static Map<String, Set<WorkOrderStatusAction>> statusHandlers = new HashMap<String, Set<WorkOrderStatusAction>>();
	
	private static final Pattern moduleIdPattern = Pattern.compile("org\\.openmrs\\.module\\.([a-z\\.]*)\\.(.*)");

	/**
	 * Attempt to identify the module ID of a class
	 * @param clazz Class to get module ID from
	 * @return String module id
	 * @should get the module ID from an openmrs module class
	 */
	public static String parseModuleIdFromClass(Class<?> clazz) {
		Matcher matcher = moduleIdPattern.matcher(clazz.getName());
		if (matcher.find())
			return matcher.group(1);
		return null;
}
	
	@Override
	public void registerWorkOrderStatusHandler(WorkOrderStatusAction action) {
		registerWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void registerWorkOrderStatusHandler(String moduleId, WorkOrderStatusAction action) {
		registerHandler(statusHandlers, moduleId, action);
	}
	
	@Override
	public void unregisterWorkOrderStatusHandler(WorkOrderStatusAction action) {
		unregisterWorkOrderStatusHandler(parseModuleIdFromClass(action.getClass()), action);
	}

	@Override
	public void unregisterWorkOrderStatusHandler(String moduleId, WorkOrderStatusAction action) {
		unregisterHandler(statusHandlers, moduleId, action);
	}
	
	public void fireStatusChanged(WorkOrder workOrder, WorkOrderStatus previousStatus) {
		for (Set<WorkOrderStatusAction> set : statusHandlers.values()) {
			for (WorkOrderStatusAction action : set) {
				action.apply(workOrder, previousStatus);
			}
		}
	}
	
	private static void registerHandler(Map<String, Set<WorkOrderStatusAction>> handlerMap, String moduleId, WorkOrderStatusAction action) {
		if (handlerMap.get(moduleId) == null)
			handlerMap.put(moduleId, new HashSet<WorkOrderStatusAction>());
		handlerMap.get(moduleId).add(action);		
	}

	private static boolean unregisterHandler(Map<String, Set<WorkOrderStatusAction>> handlerMap, String moduleId, WorkOrderStatusAction action) {
		Set<WorkOrderStatusAction> handlerSet = handlerMap.get(moduleId);
		if (handlerSet == null)
			return false;
		return handlerSet.remove(action);
	}
}
