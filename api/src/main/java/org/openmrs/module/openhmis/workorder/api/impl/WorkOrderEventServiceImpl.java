package org.openmrs.module.openhmis.workorder.api.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.workorder.api.IWorkOrderEventService;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrder;
import org.openmrs.module.openhmis.workorder.api.model.WorkOrderStatus;
import org.openmrs.module.openhmis.workorder.api.util.WorkOrderStatusAction;

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
